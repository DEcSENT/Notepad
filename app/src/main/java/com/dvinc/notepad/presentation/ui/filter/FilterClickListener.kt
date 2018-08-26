package com.dvinc.notepad.presentation.ui.filter

import com.dvinc.notepad.presentation.model.MarkerTypeUi

interface FilterClickListener {

    fun loadAllNotes()

    fun loadNotesBySpecificMarkerType(type: MarkerTypeUi)
}
