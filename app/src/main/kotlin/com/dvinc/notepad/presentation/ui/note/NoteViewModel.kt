/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dvinc.notepad.R
import com.dvinc.notepad.common.extension.onNext
import com.dvinc.notepad.common.extension.safeLaunch
import com.dvinc.notepad.domain.usecase.note.NoteUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.ui.base.BaseViewModel
import com.dvinc.notepad.presentation.ui.note.NoteViewState.ExistingNoteViewState
import com.dvinc.notepad.presentation.ui.note.NoteViewState.NewNoteViewState
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import timber.log.Timber

class NoteViewModel @AssistedInject constructor(
    @Assisted private val noteId: Long?,
    private val noteUseCase: NoteUseCase,
    private val noteMapper: NotePresentationMapper
) : BaseViewModel() {

    companion object {
        private const val TAG = "NoteViewModel"
    }

    val viewState = MutableLiveData<NoteViewState>()

    init {
        initNote(noteId)
    }

    private fun initNote(noteId: Long?) {
        viewModelScope.safeLaunch<NoteViewState>(
            launchBlock = {
                val noteViewState = if (noteId == null) {
                    NewNoteViewState
                } else {
                    val note = noteUseCase.getNoteById(noteId)
                    ExistingNoteViewState(note)
                }
                noteViewState
            },
            onSuccess = {
                viewState.onNext(it)
            },
            onError = {
                Timber.tag(TAG).e(it)
                showErrorMessage(R.string.error_while_loading_note)
            }
        )
    }

    fun onSaveButtonClick(
        noteName: String,
        noteContent: String
    ) {
        viewModelScope.safeLaunch(
            launchBlock = {
                val note = noteMapper.createNote(noteId, noteName, noteContent)
                noteUseCase.saveNote(note)
            },
            onSuccess = {
                viewCommands.onNext(CloseNoteScreen)
            },
            onError = {
                showErrorMessage(R.string.error_while_adding_note)
                Timber.tag(TAG).e(it)
            }
        )
    }

    @AssistedInject.Factory
    interface Factory {
        fun get(noteId: Long?): NoteViewModel
    }
}
