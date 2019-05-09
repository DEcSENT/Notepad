/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.note

import com.dvinc.notepad.R
import com.dvinc.notepad.common.resource.ResourceProvider
import com.dvinc.notepad.domain.usecase.marker.MarkerUseCase
import com.dvinc.notepad.domain.usecase.note.NoteUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.dvinc.notepad.presentation.ui.base.BasePresenter
import javax.inject.Inject

@Deprecated("replace by View model")
class NotePresenter @Inject constructor(
        private val noteUseCase: NoteUseCase,
        private val markerUseCase: MarkerUseCase,
        private val noteMapper: NotePresentationMapper,
        private val resProvider: ResourceProvider
) : BasePresenter<NoteView>() {

    fun initView(noteId: Long?) {
        if (noteId != null && noteId != 0L) {
            addSubscription(markerUseCase.getNoteMarkers()
                    .map { noteMapper.mapMarkers(it) }
                    .doOnSuccess { view?.showMarkers(it) }
                    .flatMap { noteUseCase.getNoteById(noteId) }
                    .subscribe(
                            {
                                view?.showNote(it)
                                view?.setEditMode(true)
                            },
                            { view?.showError(resProvider.getString(R.string.error_while_loading_note)) }
                    ))
        } else {
            addSubscription(markerUseCase.getNoteMarkers()
                    .map { noteMapper.mapMarkers(it) }
                    .subscribe(
                            {
                                view?.showMarkers(it)
                                view?.setEditMode(false)
                            },
                            { view?.showError(resProvider.getString(R.string.error_while_loading_markers)) }
                    ))
        }
    }

    fun onClickNoteButton(
            name: String,
            content: String,
            time: Long,
            markerTypeUi: MarkerTypeUi,
            noteId: Long = 0) {
        if (isNoteNameNotEmpty(name)) {
            val newNote = noteMapper.createNote(noteId, name, content, time, markerTypeUi)
            addSubscription(noteUseCase.addNote(newNote)
                    .subscribe(
                            { view?.closeScreen() },
                            { view?.showError(resProvider.getString(R.string.error_while_adding_note)) }
                    ))
        }
    }

    fun onClickEditNoteButton(noteId: Long?,
                              name: String,
                              content: String,
                              time: Long,
                              markerTypeUi: MarkerTypeUi) {
        if (isNoteNameNotEmpty(name)) {
            noteId?.let { id ->
                val newNote = noteMapper.createNote(id, name, content, time, markerTypeUi)
                addSubscription(noteUseCase.updateNote(newNote)
                        .subscribe(
                                { view?.closeScreen() },
                                { view?.showError(resProvider.getString(R.string.error_while_updating_note)) }
                        ))
            }
        }
    }

    private fun isNoteNameNotEmpty(name: String): Boolean {
        return if (name.isEmpty()) {
            view?.setNoteNameEmptyError(true)
            false
        } else {
            view?.setNoteNameEmptyError(false)
            true
        }
    }
}
