/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.notepad

import com.dvinc.notepad.R
import com.dvinc.notepad.common.resource.ResourceProvider
import com.dvinc.notepad.domain.usecase.NotepadUseCase
import com.dvinc.notepad.presentation.ui.base.BasePresenter
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import javax.inject.Inject

class NotepadPresenter @Inject constructor(
        private val notepadUseCase: NotepadUseCase,
        private val noteMapper: NotePresentationMapper,
        private val resourceProvider: ResourceProvider
) : BasePresenter<NotepadView>() {

    fun initNotes() {
        addSubscription(notepadUseCase.getNotes()
                .map { noteMapper.fromDomainToUi(it) }
                .subscribe(
                        { notes ->
                            view?.setEmptyView(notes.isEmpty())
                            view?.showNotes(notes)
                        },
                        { error -> view?.showError(error.localizedMessage) }))
    }

    fun deleteNote(noteId: Int) {
        addSubscription(notepadUseCase.deleteNote(noteId)
                .subscribe(
                        { view?.showMessage(resourceProvider.getString(R.string.note_successfully_deleted)) },
                        { error -> view?.showError(error.localizedMessage) }
                ))
    }

    fun onNoteSwiped(swipedNoteId: Int, swipedItemPosition: Int) {
        view?.showDeleteNoteDialog(swipedNoteId, swipedItemPosition)
    }
}
