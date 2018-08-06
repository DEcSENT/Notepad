/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.note

import com.dvinc.notepad.domain.usecase.NoteUseCase
import com.dvinc.notepad.presentation.ui.base.BasePresenter
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.model.MarkerTypeUi
import javax.inject.Inject

class NotePresenter
@Inject constructor(
        private val noteUseCase: NoteUseCase,
        private val noteMapper: NotePresentationMapper
) : BasePresenter<NoteView>() {

    fun initView(noteId: Long?) {
        if (noteId != null && noteId != 0L) {
            addSubscription(noteUseCase.getNoteMarkers()
                    .map { noteMapper.mapMarker(it) }
                    .doOnSuccess { view?.showMarkers(it) }
                    .flatMap { noteUseCase.getNoteById(noteId) }
                    .subscribe(
                            {
                                view?.showNote(it)
                                view?.setEditMode(true)
                            },
                            { view?.showError(it.localizedMessage) }
                    ))
        } else {
            addSubscription(noteUseCase.getNoteMarkers()
                    .map { noteMapper.mapMarker(it) }
                    .subscribe(
                            {
                                view?.showMarkers(it)
                                view?.setEditMode(false)
                            },
                            { view?.showError(it.localizedMessage) }
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
                            { view?.showError(it.localizedMessage) }
                    ))
        }
    }

    fun onClickEditNoteButton(noteId: Long?,
                              name: String,
                              content: String,
                              time: Long,
                              markerTypeUi: MarkerTypeUi) {
        if (isNoteNameNotEmpty(name)) {
            noteId?.let {
                val newNote = noteMapper.createNote(noteId, name, content, time, markerTypeUi)
                addSubscription(noteUseCase.updateNote(newNote)
                        .subscribe(
                                { view?.closeScreen() },
                                { view?.showError(it.localizedMessage) }
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
