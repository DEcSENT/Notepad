/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.note

import androidx.lifecycle.viewModelScope
import com.dvinc.core.extension.onNext
import com.dvinc.core.extension.safeLaunch
import com.dvinc.core.ui.BaseViewModel
import com.dvinc.notepad.R
import com.dvinc.notepad.common.DEFAULT_NOTE_ID
import com.dvinc.notepad.domain.usecase.note.NoteUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.ui.note.NoteViewState.ExistingNoteViewState
import com.dvinc.notepad.presentation.ui.note.NoteViewState.NewNoteViewState
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import timber.log.Timber

class NoteViewModel @AssistedInject constructor(
    @Assisted private val noteId: Long,
    private val noteUseCase: NoteUseCase,
    private val noteMapper: NotePresentationMapper
) : BaseViewModel<NoteViewState>() {

    companion object {
        private const val TAG = "NoteViewModel"
    }

    init {
        initNote(noteId)
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
                navigateBack()
            },
            onError = {
                showErrorMessage(R.string.error_while_adding_note)
                Timber.tag(TAG).e(it)
            }
        )
    }

    fun onBackClick() {
        navigateBack()
    }

    private fun initNote(noteId: Long) {
        viewModelScope.safeLaunch<NoteViewState>(
            launchBlock = {
                val noteViewState = if (noteId == DEFAULT_NOTE_ID) {
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

    @AssistedInject.Factory
    interface Factory {
        fun get(noteId: Long): NoteViewModel
    }
}
