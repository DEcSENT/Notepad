/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.notepad

import com.dvinc.notepad.presentation.ui.base.MessageView
import com.dvinc.notepad.presentation.ui.base.MvpView
import com.dvinc.notepad.presentation.model.NoteUi

interface NotepadView : MvpView, MessageView {

    fun showNotes(notes: List<NoteUi>)

    fun setEmptyView(isVisible: Boolean)

    fun showDeleteNoteDialog(notePosition: Int, swipedItemPosition: Int)
}