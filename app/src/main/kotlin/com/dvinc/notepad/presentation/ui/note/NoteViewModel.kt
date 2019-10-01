/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.note

import androidx.lifecycle.MutableLiveData
import com.dvinc.notepad.R
import com.dvinc.notepad.common.extension.onNext
import com.dvinc.notepad.domain.usecase.note.NoteUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.ui.base.BaseViewModel
import com.dvinc.notepad.presentation.ui.base.ViewCommand
import com.dvinc.notepad.presentation.ui.note.NoteViewState.ExistingNoteViewState
import com.dvinc.notepad.presentation.ui.note.NoteViewState.NewNoteViewState
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

//TODO(dv): refactor all of this
class NoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
    private val noteMapper: NotePresentationMapper
) : BaseViewModel() {

    companion object {
        private const val TAG = "NoteViewModel"
        private const val DEFAULT_NOTE_ID = 0L
    }

    val screenState = MutableLiveData<NoteViewState>()

    fun initNote(noteId: Long?) {
        // No need to load note if we have one
        if (screenState.value != null) return
        val viewStateSource = if (noteId != null && noteId != DEFAULT_NOTE_ID) {
            getNoteSource(noteId)
        } else {
            getNewNoteSource()
        }
        viewStateSource
            .subscribe(
                {
                    screenState.onNext(it)
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
        noteContent: String
    ) {
        val noteId = getCurrentNoteId()
        val note = noteMapper.createNote(noteId, noteName, noteContent)
        noteUseCase.saveNote(note)
            .subscribe(
                {
                    val closeScreenCommand = ViewCommand.CloseNoteScreen
                    viewCommands.onNext(closeScreenCommand)
                },
                {
                    showErrorMessage(R.string.error_while_adding_note)
                    Timber.tag(TAG).e(it)
                }
            )
            .disposeOnViewModelDestroy()
    }

    private fun getNewNoteSource(): Single<NewNoteViewState> {
        return Single.just(NewNoteViewState)
    }

    private fun getNoteSource(noteId: Long): Single<ExistingNoteViewState> {
        return noteUseCase.getNoteById(noteId).map {
            ExistingNoteViewState(it)
        }
    }

    private fun getCurrentNoteId(): Long {
        return (screenState.value as? ExistingNoteViewState)?.note?.id ?: DEFAULT_NOTE_ID
    }
}
