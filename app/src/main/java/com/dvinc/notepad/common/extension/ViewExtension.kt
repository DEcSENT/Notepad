/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 8/5/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.common.extension

import android.view.View

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.toggleGone(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}
