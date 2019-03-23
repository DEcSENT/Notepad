package com.dvinc.notepad.presentation.ui.base

sealed class ViewCommand {

    data class OpenNoteScreen(val noteId: Long) : ViewCommand()
}
