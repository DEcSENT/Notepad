package com.dvinc.notepad.presentation.ui.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val viewCommands = CommandsLiveData<ViewCommand>()

    protected fun showMessage(@StringRes messageResId: Int) {
        val showMessageCommand = ShowMessage(messageResId)
        viewCommands.onNext(showMessageCommand)
    }

    protected fun showErrorMessage(@StringRes messageResId: Int) {
        val showMessageCommand = ShowErrorMessage(messageResId)
        viewCommands.onNext(showMessageCommand)
    }
}
