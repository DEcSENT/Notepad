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

    private var cachedNotes: List<NoteUi>? = null

    //TODO: Update flow with current filter
    private var currentFilter: MarkerTypeUi? = null

    fun initNotes(markerType: MarkerTypeUi? = null) {
        addSubscription(notepadUseCase.getNotes()
                .map { noteMapper.fromDomainToUi(it) }
                .map { notes ->
                    updateCachedNotes(notes)
                    /* If marker type exist, then filter notes and update current filter */
                    markerType?.let { markerType ->
                        //updateCurrentFilterCache(markerType)
                        notes.filter { it.markerType == markerType }
                    } ?: notes
                }
                .subscribe(
                        { notes ->
                            updateNotepadScreen(notes)
                        },
                        { view?.showError(resProvider.getString(R.string.error_while_load_data_from_db)) }))
    }

    fun filterNotes(markerType: MarkerTypeUi) {
        cachedNotes
                ?.filter { it.markerType == markerType }
                ?.let { updateNotepadScreen(it) }
    }

    fun loadAllNotes() {
        clearCurrentSelectedMarkerType()
        cachedNotes?.let {
            updateNotepadScreen(it)
        }
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

    fun updateCurrentFilterCache(markerType: MarkerTypeUi) {
        currentFilter = markerType
        view?.storeCurrentSelectedMarkerType(markerType)
    }

    private fun clearCurrentSelectedMarkerType() {
        currentFilter = null
        view?.clearStoredCurrentSelectedMarkerType()
    }


    private fun updateNotepadScreen(notes: List<NoteUi>) {
        view?.setEmptyView(notes.isEmpty())
        view?.showNotes(notes)
    }

    private fun updateCachedNotes(notes: List<NoteUi>) {
        cachedNotes = notes
    }
}
