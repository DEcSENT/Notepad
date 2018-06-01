/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.note

import com.dvinc.notepad.domain.model.Note
import com.dvinc.notepad.domain.model.NoteMarker
import com.dvinc.notepad.ui.base.MessageView
import com.dvinc.notepad.ui.base.MvpView

interface NoteView : MvpView, MessageView {

    fun closeScreen()

    fun showMarkers(markers: List<NoteMarker>)

    fun setEditMode(isEditMode: Boolean)

    fun showNote(note: Note)

    fun setNoteNameEmptyError(isVisible: Boolean)
}
