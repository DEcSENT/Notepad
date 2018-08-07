/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.domain.common.execution

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class ThreadScheduler @Inject constructor() {

    protected open fun ui(): Scheduler = AndroidSchedulers.mainThread()

    protected open fun io(): Scheduler = Schedulers.io()

    fun <T> ioToUiSingle() = { upstream: Single<T> -> upstream.subscribeOn(io()).observeOn(ui()) }

    fun <T> ioToUiObservable() = { upstream: Observable<T> -> upstream.subscribeOn(io()).observeOn(ui()) }

    fun <T> ioToUiFlowable() = { upstream: Flowable<T> -> upstream.subscribeOn(io()).observeOn(ui()) }

    fun ioToUiCompletable() = { upstream: Completable -> upstream.subscribeOn(io()).observeOn(ui()) }
}

fun <T> Single<T>.scheduleIoToUi(scheduler: ThreadScheduler): Single<T> {
    return compose(scheduler.ioToUiSingle())
}

fun <T> Observable<T>.scheduleIoToUi(scheduler: ThreadScheduler): Observable<T> {
    return compose(scheduler.ioToUiObservable())
}

fun <T> Flowable<T>.scheduleIoToUi(scheduler: ThreadScheduler): Flowable<T> {
    return compose(scheduler.ioToUiFlowable())
}

fun Completable.scheduleIoToUi(scheduler: ThreadScheduler): Completable {
    return compose(scheduler.ioToUiCompletable())
}
