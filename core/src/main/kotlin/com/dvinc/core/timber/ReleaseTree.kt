/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.core.timber

import android.util.Log
import timber.log.Timber

class ReleaseTree : Timber.Tree() {

    companion object {
        private const val CRASHLYTICS_KEY_PRIORITY = "priority"

        private const val CRASHLYTICS_KEY_TAG = "tag"

        private const val CRASHLYTICS_KEY_MESSAGE = "message"
    }

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        val exception = throwable?.let { throwable } ?: Exception(message)

        // TODO: add crashlitycs
//        Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority)
//        Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag)
//        Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message)
//        Crashlytics.logException(exception)
    }
}
