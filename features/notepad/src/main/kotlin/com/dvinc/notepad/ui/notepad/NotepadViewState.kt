/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.notepad

import com.dvinc.core.ui.ViewState
import com.dvinc.base.notepad.presentation.model.NoteUi

data class NotepadViewState(
    val notes: List<NoteUi> = emptyList(),
    val isStubViewVisible: Boolean = false
) : ViewState
