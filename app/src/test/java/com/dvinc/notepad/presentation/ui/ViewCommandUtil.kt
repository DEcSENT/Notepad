package com.dvinc.notepad.presentation.ui

import com.dvinc.core.ui.ViewCommand
import java.util.LinkedList

object ViewCommandUtil {

    fun createViewCommandList(T: ViewCommand): LinkedList<ViewCommand> {
        return LinkedList<ViewCommand>().apply {
            add(T)
        }
    }
}
