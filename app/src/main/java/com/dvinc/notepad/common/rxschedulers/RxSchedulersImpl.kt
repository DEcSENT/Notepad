/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.common.rxschedulers

import io.reactivex.schedulers.Schedulers
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class RxSchedulersImpl : RxSchedulers() {

    override fun getMainThreadScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun getIoScheduler(): Scheduler {
        return Schedulers.io()
    }
}
 