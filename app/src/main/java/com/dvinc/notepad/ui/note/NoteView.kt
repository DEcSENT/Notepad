/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.note

import com.dvinc.notepad.ui.base.MvpView

interface NoteView : MvpView {
    fun closeScreen()
    fun showError(message: String)
}
