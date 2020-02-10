/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.core.di.component

import com.dvinc.core.di.DaggerApplication
import com.dvinc.core.di.module.AppToolsModule
import com.dvinc.core.di.provider.AppToolsProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppToolsModule::class])
interface AppToolsComponent : AppToolsProvider {

    @Component.Builder
    interface ComponentBuilder {

        @BindsInstance
        fun application(daggerApplication: DaggerApplication): ComponentBuilder

        fun build(): AppToolsComponent
    }

    class Builder private constructor() {

        companion object {

            fun build(daggerApplication: DaggerApplication): AppToolsProvider {

                return DaggerAppToolsComponent.builder()
                    .application(daggerApplication)
                    .build()
            }
        }
    }
}
