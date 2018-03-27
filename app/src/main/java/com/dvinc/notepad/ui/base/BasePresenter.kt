/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.base

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<in T : MvpView> {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var view: T? = null

    fun attachView(mvpView: T) {
        view = mvpView
    }

    fun detachView() {
        view = null
        compositeDisposable.clear()
    }

    fun addSubscription(disposable: CompositeDisposable) {
        compositeDisposable.add(disposable)
    }
}
