package com.dvinc.notepad.domain

import com.dvinc.notepad.common.execution.ThreadScheduler
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class TestThreadScheduler @Inject constructor() : ThreadScheduler() {

    override fun ui(): Scheduler = Schedulers.trampoline()

    override fun io(): Scheduler = Schedulers.trampoline()
}
