/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.core.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvinc.core.ui.CommandsLiveData
import java.util.LinkedList

fun <T> MutableLiveData<T>.onNext(next: T) {
    value = next
}

inline fun <reified T : Any> LiveData<T>.requireValue(): T {
    return requireNotNull(value)
}

inline fun <reified T : Any> MutableLiveData<T>.update(
    update: (T) -> T
) {
    value = update.invoke(requireValue())
}

inline fun <reified T : Any, reified L : LiveData<T>> Fragment.observe(
    liveData: L,
    noinline block: (T) -> Unit
) {
    liveData.observe(this.viewLifecycleOwner, Observer<T> { it?.let { block.invoke(it) } })
}

inline fun <reified T : Any, reified L : CommandsLiveData<T>> LifecycleOwner.observe(
    liveData: L,
    noinline block: (T) -> Unit
) {
    liveData.observe(this, Observer<LinkedList<T>> { commands ->
        if (commands == null) {
            return@Observer
        }
        var command: T?
        do {
            command = commands.poll()
            if (command != null) {
                block.invoke(command)
            }
        } while (command != null)
    })
}

inline fun <reified VM : ViewModel> Fragment.viewModels(
    crossinline viewModelProducer: () -> VM
): Lazy<VM> {
    return lazy(LazyThreadSafetyMode.NONE) { createViewModel { viewModelProducer() } }
}

inline fun <reified VM : ViewModel> Fragment.createViewModel(
    crossinline viewModelProducer: () -> VM
): VM {
    val factory = object : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <VM : ViewModel> create(modelClass: Class<VM>) = viewModelProducer() as VM
    }
    val viewModelProvider = ViewModelProvider(this, factory)
    return viewModelProvider[VM::class.java]
}
