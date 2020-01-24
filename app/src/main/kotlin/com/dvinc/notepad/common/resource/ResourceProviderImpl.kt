/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.common.resource

import android.content.Context
import javax.inject.Inject

class ResourceProviderImpl @Inject constructor(
    private val context: Context
) : ResourceProvider {

    override fun getString(resId: Int): String {
        return context.getString(resId)
    }
}
