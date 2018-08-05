/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<T : MvpView> {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    var view: T? = null

    fun attachView(mvpView: T) {
        view = mvpView
    }

    fun detachView() {
        view = null
        compositeDisposable.clear()
    }

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}
