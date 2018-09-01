/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.notepad

import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.dvinc.notepad.presentation.ui.base.MessageView
import com.dvinc.notepad.presentation.ui.base.MvpView
import com.dvinc.notepad.presentation.model.NoteUi
import com.dvinc.notepad.presentation.ui.base.ErrorView

interface NotepadView : MvpView, MessageView, ErrorView {

    fun showNotes(notes: List<NoteUi>)

    fun setEmptyView(isVisible: Boolean)

    fun showDeleteNoteDialog(notePosition: Int, swipedItemPosition: Int)

    fun storeCurrentSelectedMarkerType(type: MarkerTypeUi?)

    fun clearStoredCurrentSelectedMarkerType()

    fun showCurrentFilterIcon(markerTypeUi: MarkerTypeUi)

    fun hideCurrentFilterIcon()
}
