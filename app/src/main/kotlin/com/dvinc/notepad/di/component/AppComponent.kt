/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.di.component

import com.dvinc.core.di.component.AppToolsComponent
import com.dvinc.core.di.component.DaggerDatabaseComponent
import com.dvinc.core.di.provider.AppToolsProvider
import com.dvinc.core.di.provider.ApplicationProvider
import com.dvinc.core.di.provider.DatabaseProvider
import com.dvinc.notepad.NotepadApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        AppToolsProvider::class,
        DatabaseProvider::class
    ]
)
interface AppComponent : ApplicationProvider {

    class Builder private constructor() {

        companion object {

            fun build(application: NotepadApplication): AppComponent {

                val appToolsProvider = AppToolsComponent.Builder.build(application)

                val databaseProvider = DaggerDatabaseComponent.builder()
                    .appToolsProvider(appToolsProvider)
                    .build()

                return DaggerAppComponent.builder()
                    .appToolsProvider(appToolsProvider)
                    .databaseProvider(databaseProvider)
                    .build()
            }
        }
    }
}
