/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.filter

import com.dvinc.notepad.domain.usecase.marker.MarkerUseCase
import com.dvinc.notepad.presentation.ui.base.BasePresenter
import javax.inject.Inject

class FilterPresenter @Inject constructor(
        private val markerUseCase: MarkerUseCase
) : BasePresenter<FilterView>() {

    fun initMarkers() {

    }
}
