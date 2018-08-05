/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.notepad

import com.dvinc.notepad.domain.usecase.NotepadUseCase
import com.dvinc.notepad.ui.base.BasePresenter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotepadPresenter
@Inject constructor(
        private val notepadUseCase: NotepadUseCase
) : BasePresenter<NotepadView>() {

    fun initNotes() {
        addSubscription(notepadUseCase.getNotes().subscribe(
                { notes ->
                    view?.setEmptyView(notes.isEmpty())
                    view?.showNotes(notes)
                },
                { error -> view?.showError(error.localizedMessage) }))
    }

    //TODO: Add message provider
    fun deleteNote(noteId: Int) {
        addSubscription(notepadUseCase.deleteNote(noteId).subscribe(
                { view?.showMessage("Note successfully deleted") },
                { error -> view?.showError(error.localizedMessage) }
        ))
    }

    fun onNoteSwiped(swipedNoteId: Int, swipedItemPosition: Int) {
        view?.showDeleteNoteDialog(swipedNoteId, swipedItemPosition)
    }
}
