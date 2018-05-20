/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.note

import com.dvinc.notepad.domain.interactors.NotesInteractor
import com.dvinc.notepad.ui.base.BasePresenter
import javax.inject.Inject

class NotePresenter
@Inject constructor(
        private val notesInteractor: NotesInteractor
) : BasePresenter<NoteView>() {

    fun initView(noteId: Long?) {
        addSubscription(notesInteractor.getNoteMarkers()
                .doOnSuccess { view?.showMarkers(it) }
                .flatMap { notesInteractor.getNoteById(noteId) }
                .subscribe(
                        {
                            if (it.id != 0L) {
                                view?.showNote(it)
                                view?.setNoteButton(true)
                            }
                        },
                        { view?.showError(it.localizedMessage) }
                ))
    }

    fun saveNewNote(
            name: String,
            content: String,
            time: Long,
            markerId: Int) {
        addSubscription(notesInteractor.addNote(name, content, time, markerId)
                .subscribe(
                        { view?.closeScreen() },
                        { view?.showError(it.localizedMessage) }
                ))
    }

    fun editNote(
            noteId: Long,
            name: String,
            content: String,
            time: Long,
            markerId: Int) {
        addSubscription(notesInteractor.updateNote(noteId, name, content, time, markerId)
                .subscribe(
                        { view?.closeScreen() },
                        { view?.showError(it.localizedMessage) }
                ))
    }
}
