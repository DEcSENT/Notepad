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
        if (noteId != null && noteId != 0L) {
            addSubscription(notesInteractor.getNoteMarkers()
                    .doOnSuccess { view?.showMarkers(it) }
                    .flatMap { notesInteractor.getNoteById(noteId) }
                    .subscribe(
                            {
                                view?.showNote(it)
                                view?.setEditMode(true)
                            },
                            { view?.showError(it.localizedMessage) }
                    ))
        } else {
            view?.setEditMode(false)
        }
    }

    fun onClickNoteButton(
            noteId: Long?,
            name: String,
            content: String,
            time: Long,
            markerId: Int) {
        if (isNoteNameNotEmpty(name)) {
            addSubscription(notesInteractor.addNoteInfo(noteId, name, content, time, markerId)
                    .subscribe(
                            { view?.closeScreen() },
                            { view?.showError(it.localizedMessage) }
                    ))
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
