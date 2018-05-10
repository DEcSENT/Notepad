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

    fun initView() {
        addSubscription(notesInteractor.getMarkers()
                .subscribe(
                        { view?.showMarkers(it) },
                        { view?.showError(it.localizedMessage) }
                ))
    }

    fun saveNewNote(
            name: String,
            content: String,
            time: Long,
            markerColor: String,
            markerText: String) {
        addSubscription(notesInteractor.addNote(name, content, time, markerColor, markerText)
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
            markerColor: String,
            markerText: String) {
        addSubscription(notesInteractor.updateNote(noteId, name, content, time, markerColor, markerText)
                .subscribe(
                        { view?.closeScreen() },
                        { view?.showError(it.localizedMessage) }
                ))
    }
}
