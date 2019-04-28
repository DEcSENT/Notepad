package com.dvinc.notepad.presentation.ui.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    val commands = CommandsLiveData<ViewCommand>()

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    protected fun showMessage(@StringRes messageResId: Int) {
        val showMessageCommand = ViewCommand.ShowMessage(messageResId)
        commands.onNext(showMessageCommand)
    }

    protected fun Disposable.disposeOnViewModelDestroy(): Disposable {
        compositeDisposable.add(this)
        return this
    }
}
