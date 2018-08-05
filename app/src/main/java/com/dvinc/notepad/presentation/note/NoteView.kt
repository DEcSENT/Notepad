/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.note

import com.dvinc.notepad.domain.model.Note
import com.dvinc.notepad.presentation.base.MessageView
import com.dvinc.notepad.presentation.base.MvpView
import com.dvinc.notepad.presentation.model.MarkerTypeUi

interface NoteView : MvpView, MessageView {

    fun closeScreen()

    fun showMarkers(markers: List<MarkerTypeUi>)

    fun setEditMode(isEditMode: Boolean)

    fun showNote(note: Note)

    fun setNoteNameEmptyError(isVisible: Boolean)
}
