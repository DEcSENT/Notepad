/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.filter

import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.dvinc.notepad.presentation.ui.base.ErrorView
import com.dvinc.notepad.presentation.ui.base.MvpView

interface FilterView : MvpView, ErrorView {

    fun showMarkers(markers: List<MarkerTypeUi>)

    fun filterNotesByMarkerType(type: MarkerTypeUi)

    fun clearFilter()

    fun closeScreen()
}
