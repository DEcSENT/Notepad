/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.notepad

import com.dvinc.notepad.data.repository.NotesRepository
import com.dvinc.notepad.ui.base.BasePresenter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotepadPresenter
@Inject constructor(
        private val notesRepository: NotesRepository
) : BasePresenter<NotepadView>() {

    fun initNotes() {
        addSubscription(notesRepository.getNotes().subscribe(
                { notes -> view?.showNotes(notes) },
                { error -> view?.showError(error.localizedMessage) }))
    }

    fun deleteNote(noteId: Int) {
        addSubscription(notesRepository.deleteNote(noteId).subscribe(
                { view?.showDeletedNoteMessage() },
                { error -> view?.showError(error.localizedMessage) }
        ))
    }

    fun onNoteSwiped(swipedNoteId: Int, swipedItemPosition: Int) {
        view?.showDeleteNoteDialog(swipedNoteId, swipedItemPosition)
    }
}
