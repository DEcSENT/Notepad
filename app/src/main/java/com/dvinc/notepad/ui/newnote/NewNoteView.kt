/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.newnote

import com.dvinc.notepad.ui.base.MvpView

interface NewNoteView : MvpView {
    fun closeScreen()
    fun showError(message: String)
}
