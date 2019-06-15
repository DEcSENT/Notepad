package com.dvinc.notepad.presentation.ui

import com.dvinc.notepad.presentation.ui.base.ViewCommand
import java.util.*

object ViewCommandUtil {

    fun createViewCommandList(T: ViewCommand): LinkedList<ViewCommand> {
        return LinkedList<ViewCommand>().apply {
            add(T)
        }
    }
}
