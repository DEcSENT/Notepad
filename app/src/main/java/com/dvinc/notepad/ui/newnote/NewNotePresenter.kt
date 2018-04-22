/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.newnote

import com.dvinc.notepad.data.database.entity.Note
import com.dvinc.notepad.data.repository.NotesRepository
import com.dvinc.notepad.ui.base.BasePresenter
import javax.inject.Inject

class NewNotePresenter @Inject constructor(private val repository: NotesRepository) : BasePresenter<NewNoteView>() {

    fun saveNewNote(name: String, content: String, time: Long) {
        addSubscription(repository.addNote(Note(0, name, content, time)).subscribe(
                { view?.closeScreen() },
                { view?.showError(it.localizedMessage) }
        ))
    }
}
