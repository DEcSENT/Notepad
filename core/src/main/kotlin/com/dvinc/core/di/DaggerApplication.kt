/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.core.di

import android.content.Context
import com.dvinc.core.di.provider.ApplicationProvider

interface DaggerApplication {

    fun getApplicationContext(): Context

    fun getApplicationProvider(): ApplicationProvider
}
