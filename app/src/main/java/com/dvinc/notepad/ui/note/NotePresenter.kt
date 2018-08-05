/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.note

import com.dvinc.notepad.domain.usecase.NoteUseCase
import com.dvinc.notepad.ui.base.BasePresenter
import javax.inject.Inject

class NotePresenter
@Inject constructor(
        private val noteUseCase: NoteUseCase
) : BasePresenter<NoteView>() {

    fun initView(noteId: Long?) {
        if (noteId != null && noteId != 0L) {
            addSubscription(noteUseCase.getNoteMarkers()
                    .doOnSuccess { view?.showMarkers(it) }
                    .flatMap { noteUseCase.getNoteById(noteId) }
                    .subscribe(
                            {
                                view?.showNote(it)
                                view?.setEditMode(true)
                            },
                            { view?.showError(it.localizedMessage) }
                    ))
        } else {
            addSubscription(noteUseCase.getNoteMarkers()
                    .subscribe(
                            {
                                view?.showMarkers(it)
                                view?.setEditMode(false)
                            },
                            { view?.showError(it.localizedMessage) }
                    ))
        }
    }

    fun onClickNoteButton(
            noteId: Long?,
            name: String,
            content: String,
            time: Long,
            markerId: Int) {
        if (isNoteNameNotEmpty(name)) {
            addSubscription(noteUseCase.addNoteInfo(noteId, name, content, time, markerId)
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
