package com.dvinc.notepad.presentation.ui.base

import androidx.lifecycle.MutableLiveData
import java.util.*

class CommandsLiveData<T> : MutableLiveData<LinkedList<T>>() {

    fun onNext(value: T) {
        var commands = getValue()
        if (commands == null) {
            commands = LinkedList()
        }
        commands.add(value)
        setValue(commands)
    }
}
