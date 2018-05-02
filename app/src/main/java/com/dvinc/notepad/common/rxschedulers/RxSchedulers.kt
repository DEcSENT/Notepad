/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.common.rxschedulers

import io.reactivex.*

abstract class RxSchedulers {

    abstract fun getMainThreadScheduler(): Scheduler
    abstract fun getIoScheduler(): Scheduler

    fun <T> getIoToMainTransformerSingle(): SingleTransformer<T, T> {
        return SingleTransformer { objectObservable -> objectObservable
                    .subscribeOn(getIoScheduler())
                    .observeOn(getMainThreadScheduler())
        }
    }

    fun <T> getIoToMainTransformerFlowable(): FlowableTransformer<T, T> {
        return FlowableTransformer { objectObservable -> objectObservable
                    .subscribeOn(getIoScheduler())
                    .observeOn(getMainThreadScheduler())
        }
    }

    fun getIoToMainTransformerCompletable(): CompletableTransformer {
        return CompletableTransformer { objectObservable -> objectObservable
                    .subscribeOn(getIoScheduler())
                    .observeOn(getMainThreadScheduler())
        }
    }
}
