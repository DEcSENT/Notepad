/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.notepad

import com.dvinc.notepad.data.NotesRepository
import com.dvinc.notepad.ui.base.BasePresenter

class NotepadPresenter : BasePresenter<NotepadView>() {

    private val notesRepository: NotesRepository = NotesRepository()

    fun initNotes() {
        addSubscription(notesRepository.getNotes().subscribe(
                { notes -> view?.showNotes(notes) },
                { error -> view?.showError(error.localizedMessage) }))
    }
}
