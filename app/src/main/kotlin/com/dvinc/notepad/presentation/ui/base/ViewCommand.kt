package com.dvinc.notepad.presentation.ui.base

sealed class ViewCommand {

    /* Common commands region */
    data class ShowMessage(val messageResId: Int) : ViewCommand()

    data class ShowErrorMessage(val messageResId: Int) : ViewCommand()
    /* Common commands region end*/

    /* Notepad screen commands region start */
    data class OpenNoteScreen(val noteId: Long) : ViewCommand()
    /* Notepad screen commands region end */

    /* Note screen commands region start */
    object CloseNoteScreen : ViewCommand()
    /* Note screen commands region end */
}
