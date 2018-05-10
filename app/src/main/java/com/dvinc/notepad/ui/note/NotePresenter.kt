/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.note

import com.dvinc.notepad.domain.interactors.NotesInteractor
import com.dvinc.notepad.ui.base.BasePresenter
import io.reactivex.Single
import javax.inject.Inject

class NotePresenter
@Inject constructor(
        private val notesInteractor: NotesInteractor
) : BasePresenter<NoteView>() {

    fun initView(noteId: Long?) {
        addSubscription(notesInteractor.getMarkers()
                .doOnSuccess { view?.showMarkers(it) }
                .flatMap {
                    Single.just(
                            when (noteId) {
                                null -> 0
                                else -> noteId
                            })
                }
                .filter { noteId != 0L }
                .flatMapSingle { notesInteractor.getNoteById(it) }
                .subscribe(
                        {
                            view?.showNote(it);
                            view?.setNoteButton(true)
                        },
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
