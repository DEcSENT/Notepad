/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.core.di.module

import android.content.Context
import com.dvinc.core.di.DaggerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppToolsModule {

    @Singleton
    @Provides
    fun provideApplicationContext(application: DaggerApplication): Context {
        return application.getApplicationContext()
    }
}
