package com.dvinc.core.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections

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

    protected fun navigateTo(navDirection: NavDirections) {
        val navToCommand = NavigateTo(navDirection)
        viewCommands.onNext(navToCommand)
    }

    protected fun navigateBack() {
        viewCommands.onNext(NavigateUp)
    }
}
