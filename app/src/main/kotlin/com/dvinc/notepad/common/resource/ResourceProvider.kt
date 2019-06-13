/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.common.resource

import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes resId: Int): String
}
