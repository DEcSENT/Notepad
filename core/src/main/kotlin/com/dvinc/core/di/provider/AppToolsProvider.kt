/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.core.di.provider

import android.content.Context

interface AppToolsProvider {

    fun provideAppContext(): Context
}
