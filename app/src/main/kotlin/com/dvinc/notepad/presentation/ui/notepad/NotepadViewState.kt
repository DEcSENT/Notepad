/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.notepad

import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.dvinc.notepad.presentation.model.NoteUi

sealed class NotepadViewState {

    abstract class BaseContent(open val notes: List<NoteUi>) : NotepadViewState()

    data class Content(override val notes: List<NoteUi>) : BaseContent(notes)

    data class FilteredContent(
        override val notes: List<NoteUi>,
        val filteredNotes: List<NoteUi>,
        val currentMarkerType: MarkerTypeUi
    ) : BaseContent(notes)
}
