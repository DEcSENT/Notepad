/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.notepad

import com.dvinc.notepad.domain.interactors.NotesInteractor
import com.dvinc.notepad.ui.base.BasePresenter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotepadPresenter
@Inject constructor(
        private val notesInteractor: NotesInteractor
) : BasePresenter<NotepadView>() {

    fun initNotes() {
        addSubscription(notesInteractor.getNotes().subscribe(
                { notes ->
                    view?.setEmptyView(notes.isEmpty())
                    view?.showNotes(notes)
                },
                { error -> view?.showError(error.localizedMessage) }))
    }

    fun deleteNote(noteId: Int) {
        addSubscription(notesInteractor.deleteNote(noteId).subscribe(
                { view?.showDeletedNoteMessage() },
                { error -> view?.showError(error.localizedMessage) }
        ))
    }

    fun onNoteSwiped(swipedNoteId: Int, swipedItemPosition: Int) {
        view?.showDeleteNoteDialog(swipedNoteId, swipedItemPosition)
    }
}
