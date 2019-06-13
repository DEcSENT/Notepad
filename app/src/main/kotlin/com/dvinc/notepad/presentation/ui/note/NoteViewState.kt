/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.note

import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.presentation.model.MarkerTypeUi

sealed class NoteViewState {

    data class NewNoteViewState(val availableMarkers: List<MarkerTypeUi>) : NoteViewState()

    data class ExistingNoteViewState(
        val note: Note,
        val availableMarkers: List<MarkerTypeUi>
    ) : NoteViewState()
}
