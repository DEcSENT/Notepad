/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.note

import com.dvinc.notepad.data.database.entity.Note
import com.dvinc.notepad.domain.interactors.NotesInteractor
import com.dvinc.notepad.ui.base.BasePresenter
import javax.inject.Inject

class NotePresenter
@Inject constructor(
        private val notesInteractor: NotesInteractor
) : BasePresenter<NoteView>() {

    fun saveNewNote(name: String, content: String, time: Long) {
        addSubscription(notesInteractor.addNote(Note(0, name, content, time)).subscribe(
                { view?.closeScreen() },
                { view?.showError(it.localizedMessage) }
        ))
    }
}
