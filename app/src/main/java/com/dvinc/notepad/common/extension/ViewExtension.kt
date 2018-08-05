/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 8/5/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.common.extension

import android.view.View

fun View.visible(isVisible: Boolean) =
        when (isVisible) {
            true -> this.visibility = View.VISIBLE
            false -> this.visibility = View.GONE
        }
