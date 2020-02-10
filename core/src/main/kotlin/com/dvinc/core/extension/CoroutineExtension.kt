/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.core.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.safeLaunch(
    launchBlock: suspend () -> Unit,
    onSuccess: () -> Unit,
    onError: (Throwable) -> Unit
) {
    launch {
        try {
            launchBlock.invoke()
            onSuccess.invoke()
        } catch (t: Throwable) {
            onError.invoke(t)
        }
    }
}

fun <T> CoroutineScope.safeLaunch(
    launchBlock: suspend () -> T,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
) {
    launch {
        try {
            val result = launchBlock.invoke()
            onSuccess.invoke(result)
        } catch (t: Throwable) {
            onError.invoke(t)
        }
    }
}
