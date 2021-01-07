package com.dvinc.archive.di.component

import com.dvinc.archive.di.module.ArchiveModule
import com.dvinc.core.di.provider.ApplicationProvider
import com.dvinc.archive.ui.archive.ArchiveFragment
import dagger.Component

@Component(
    modules = [
        ArchiveModule::class
    ],
    dependencies = [
        ApplicationProvider::class
    ]
)
interface ArchiveComponent {

    fun inject(target: ArchiveFragment)

    @Component.Factory
    interface Factory {
        fun create(applicationProvider: ApplicationProvider): ArchiveComponent
    }
}
