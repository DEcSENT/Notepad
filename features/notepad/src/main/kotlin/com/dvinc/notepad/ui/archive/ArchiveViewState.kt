/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.archive

import com.dvinc.core.ui.ViewState
import com.dvinc.base.notepad.presentation.model.NoteUi

data class ArchiveViewState(
    val archivedNotes: List<NoteUi> = emptyList(),
    val isStubViewVisible: Boolean = false
) : ViewState
