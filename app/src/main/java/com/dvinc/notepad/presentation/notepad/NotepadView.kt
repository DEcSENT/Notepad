/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.notepad

import com.dvinc.notepad.presentation.base.MessageView
import com.dvinc.notepad.presentation.base.MvpView
import com.dvinc.notepad.presentation.model.NoteUi

interface NotepadView : MvpView, MessageView {

    fun showNotes(notes: List<NoteUi>)

    fun setEmptyView(isVisible: Boolean)

    fun showDeleteNoteDialog(notePosition: Int, swipedItemPosition: Int)
}
