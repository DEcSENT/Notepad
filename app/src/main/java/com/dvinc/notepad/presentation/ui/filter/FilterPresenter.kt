/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.filter

import com.dvinc.notepad.R
import com.dvinc.notepad.common.resource.ResourceProvider
import com.dvinc.notepad.domain.usecase.marker.MarkerUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.dvinc.notepad.presentation.ui.base.BasePresenter
import javax.inject.Inject

class FilterPresenter @Inject constructor(
        private val markerUseCase: MarkerUseCase,
        private val noteMapper: NotePresentationMapper,
        private val resProvider: ResourceProvider
) : BasePresenter<FilterView>() {

    fun initMarkers() {
        addSubscription(markerUseCase.getNoteMarkers()
                .map { noteMapper.mapMarker(it) }
                .subscribe(
                        { view?.showMarkers(it) },
                        { view?.showError(resProvider.getString(R.string.error_while_loading_markers)) }
                ))
    }

    fun onMarkerItemClick(type: MarkerTypeUi) {
        view?.filterNotesByMarkerType(type)
        view?.closeScreen()
    }

    fun onClearFilterClick() {
        view?.clearFilter()
        view?.closeScreen()
    }
}
