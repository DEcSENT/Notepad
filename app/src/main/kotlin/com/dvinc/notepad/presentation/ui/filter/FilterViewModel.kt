/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.filter

import androidx.lifecycle.MutableLiveData
import com.dvinc.notepad.domain.usecase.marker.MarkerUseCase
import com.dvinc.notepad.presentation.mapper.NotePresentationMapper
import com.dvinc.notepad.presentation.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class FilterViewModel @Inject constructor(
    private val markerUseCase: MarkerUseCase,
    private val noteMapper: NotePresentationMapper
) : BaseViewModel() {

    companion object {
        private const val TAG = "FilterViewModel"
    }

    val state = MutableLiveData<FilterViewState>()


    init {
        loadMarkers()
    }

    private fun loadMarkers() {
        markerUseCase.getNoteMarkers()
            .map(noteMapper::mapMarkers)
            .subscribe(
                {
                    val filterViewState = FilterViewState(it)
                    state.value = filterViewState
                },
                {
                    Timber.tag(TAG).e(it)
                }
            )
            .disposeOnViewModelDestroy()
    }
}
