package com.dvinc.notepad.presentation.ui.archive

import com.dvinc.notepad.presentation.model.NoteUi

data class ArchiveViewState(
    val archivedNotes: List<NoteUi> = emptyList(),
    val isStubViewVisible: Boolean = false
)
