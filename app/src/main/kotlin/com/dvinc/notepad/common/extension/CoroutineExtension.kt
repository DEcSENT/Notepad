/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.common.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.safeLaunch(
    launchBlock: suspend () -> Unit,
    onSuccess: () -> Unit,
    onError: (Throwable) -> Unit
) {
    launch {
        try {
            // .invoke() doesn't work here: https://youtrack.jetbrains.com/issue/KT-31269
            launchBlock()
            onSuccess.invoke()
        } catch (t: Throwable) {
            onError.invoke(t)
        }
    }
}
