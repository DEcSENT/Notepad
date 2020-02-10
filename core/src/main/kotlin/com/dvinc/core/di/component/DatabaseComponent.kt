/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.core.di.component

import com.dvinc.core.di.module.DatabaseModule
import com.dvinc.core.di.provider.AppToolsProvider
import com.dvinc.core.di.provider.DatabaseProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        AppToolsProvider::class
    ],
    modules = [
        DatabaseModule::class
    ]
)
interface DatabaseComponent : DatabaseProvider
