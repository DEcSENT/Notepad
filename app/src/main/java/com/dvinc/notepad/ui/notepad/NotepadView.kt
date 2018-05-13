/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.notepad

import com.dvinc.notepad.domain.model.Note
import com.dvinc.notepad.ui.base.MessageView
import com.dvinc.notepad.ui.base.MvpView

interface NotepadView : MvpView, MessageView {

    fun showNotes(notes: List<Note>)

    fun setEmptyView(isVisible: Boolean)

    fun showDeleteNoteDialog(notePosition: Int, swipedItemPosition: Int)
}
