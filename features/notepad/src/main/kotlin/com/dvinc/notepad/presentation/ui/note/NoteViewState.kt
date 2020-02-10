/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.note

import com.dvinc.notepad.domain.model.note.Note

sealed class NoteViewState {

    object NewNoteViewState : NoteViewState()

    data class ExistingNoteViewState(
        val note: Note
    ) : NoteViewState()
}
