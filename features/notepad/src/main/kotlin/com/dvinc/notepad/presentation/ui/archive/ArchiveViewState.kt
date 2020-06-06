/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.archive

import com.dvinc.core.ui.ViewState
import com.dvinc.notepad.presentation.model.NoteUi

data class ArchiveViewState(
    val archivedNotes: List<NoteUi> = emptyList(),
    val isStubViewVisible: Boolean = false
) : ViewState