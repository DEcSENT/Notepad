/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.note

import androidx.lifecycle.MutableLiveData
import com.dvinc.notepad.R
import com.dvinc.notepad.common.extension.onNext
import com.dvinc.notepad.domain.usecase.marker.MarkerUseCase
import com.dvinc.notepad.domain.usecase.note.NoteUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.dvinc.notepad.presentation.ui.base.BaseViewModel
import com.dvinc.notepad.presentation.ui.base.ViewCommand
import com.dvinc.notepad.presentation.ui.note.NoteViewState.ExistingNoteViewState
import com.dvinc.notepad.presentation.ui.note.NoteViewState.NewNoteViewState
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import timber.log.Timber
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
    private val markerUseCase: MarkerUseCase,
    private val noteMapper: NotePresentationMapper
) : BaseViewModel() {

    companion object {
        private const val TAG = "NoteViewModel"
    }

    val state = MutableLiveData<NoteViewState>()

    fun initNote(noteId: Long?) {
        // No need to load note if we have one
        if (state.value != null) return
        val viewStateSource = if (noteId != null && noteId != 0L) {
            getNoteSource(noteId)
        } else {
            getNewNoteSource()
        }
        viewStateSource
            .subscribe(
                {
                    state.onNext(it)
                },
                {
                    Timber.tag(TAG).e(it)
                    showErrorMessage(R.string.error_while_loading_note)
                }
            )
            .disposeOnViewModelDestroy()
    }

    fun onSaveButtonClick(
        noteName: String,
        noteContent: String,
        noteMarkerType: MarkerTypeUi
    ) {
        val noteId = getCurrentNoteId()
        val currentTime = System.currentTimeMillis()
        val note = noteMapper.createNote(noteId, noteName, noteContent, currentTime, noteMarkerType)
        noteUseCase.saveNote(note)
            .subscribe(
                {
                    val closeScreenCommand = ViewCommand.CloseNoteScreen
                    commands.onNext(closeScreenCommand)
                },
                {
                    showErrorMessage(R.string.error_while_adding_note)
                    Timber.tag(TAG).e(it)
                }
            )
            .disposeOnViewModelDestroy()
    }

    private fun getNewNoteSource(): Single<NewNoteViewState> {
        return markerUseCase.getNoteMarkers()
            .map { noteMapper.mapMarkers(it) }
            .map { NewNoteViewState(it) }
    }

    private fun getNoteSource(noteId: Long): Single<ExistingNoteViewState> {
        return markerUseCase.getNoteMarkers()
            .map { noteMapper.mapMarkers(it) }
            .zipWith(noteUseCase.getNoteById(noteId), BiFunction { markers, note ->
                ExistingNoteViewState(note, markers)
            })
    }

    private fun getCurrentNoteId(): Long {
        return (state.value as? ExistingNoteViewState)?.note?.id ?: 0
    }
}
