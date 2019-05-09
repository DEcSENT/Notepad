/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.note

import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.dvinc.notepad.presentation.ui.base.ErrorView
import com.dvinc.notepad.presentation.ui.base.MvpView

@Deprecated("remove when view model will be ready")
interface NoteView : MvpView, ErrorView {

    fun closeScreen()

    fun showMarkers(markers: List<MarkerTypeUi>)

    fun setEditMode(isEditMode: Boolean)

    fun showNote(note: Note)

    fun setNoteNameEmptyError(isVisible: Boolean)
}
