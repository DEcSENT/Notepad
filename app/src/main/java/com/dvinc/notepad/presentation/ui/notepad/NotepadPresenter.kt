/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.notepad

import com.dvinc.notepad.R
import com.dvinc.notepad.common.resource.ResourceProvider
import com.dvinc.notepad.domain.usecase.notepad.NotepadUseCase
import com.dvinc.notepad.presentation.ui.base.BasePresenter
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.dvinc.notepad.presentation.model.NoteUi
import javax.inject.Inject

class NotepadPresenter @Inject constructor(
        private val notepadUseCase: NotepadUseCase,
        private val noteMapper: NotePresentationMapper,
        private val resProvider: ResourceProvider
) : BasePresenter<NotepadView>() {

    private var currentFilter: MarkerTypeUi? = null

    fun initNotes(markerType: MarkerTypeUi? = null) {
        setCurrentFilterIcon(markerType)
        addSubscription(notepadUseCase.getNotes()
                .map { noteMapper.fromDomainToUi(it) }
                .map { notes ->
                    /* If current cached filter exist, then filter notes.
                    Else filter by method parameter or return all notes. */
                    currentFilter?.let { markerType ->
                        return@map notes.filter { it.markerType == markerType }
                    }
                    markerType?.let { markerType ->
                        notes.filter { it.markerType == markerType }
                    } ?: notes
                }
                .subscribe(
                        { notes ->
                            view?.setEmptyView(notes.isEmpty())
                            view?.showNotes(notes)
                        },
                        { view?.showError(resProvider.getString(R.string.error_while_load_data_from_db)) }))
    }

    fun filterNotes(markerType: MarkerTypeUi) {
        initNotes(markerType)
        currentFilter = markerType
        view?.storeCurrentSelectedMarkerType(markerType)
    }

    fun loadAllNotes() {
        initNotes()
        currentFilter = null
        view?.clearStoredCurrentSelectedMarkerType()
    }

    fun deleteNote(noteId: Int) {
        addSubscription(notepadUseCase.deleteNote(noteId)
                .subscribe(
                        { view?.showMessage(resProvider.getString(R.string.note_successfully_deleted)) },
                        { view?.showError(resProvider.getString(R.string.error_while_deleting_note)) }
                ))
    }

    fun onNoteSwiped(swipedNoteId: Int, swipedItemPosition: Int) {
        view?.showDeleteNoteDialog(swipedNoteId, swipedItemPosition)
    }

    fun onNoteItemClick(note: NoteUi) {
        view?.goToNoteScreen(note.id)
    }

    private fun setCurrentFilterIcon(markerType: MarkerTypeUi?) {
        markerType?.let {
            view?.showCurrentFilterIcon(it)
        } ?: view?.hideCurrentFilterIcon()
    }
}
