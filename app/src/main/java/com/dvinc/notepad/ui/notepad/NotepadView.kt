/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.notepad

import com.dvinc.notepad.data.database.entity.Note
import com.dvinc.notepad.ui.base.MvpView

interface NotepadView : MvpView {
    fun showError(message: String)
    fun showNotes(notes: List<Note>)
    fun showDeletedNoteMessage()
    fun showDeleteNoteDialog(notePosition: Int, swipedItemPosition: Int)
}
