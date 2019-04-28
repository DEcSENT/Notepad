/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.notepad

import com.dvinc.notepad.presentation.model.NoteUi

sealed class NotepadViewState {

    data class Content(val notes: List<NoteUi>) : NotepadViewState()
}
