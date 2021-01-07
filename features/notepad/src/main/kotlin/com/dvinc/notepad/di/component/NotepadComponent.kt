/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.di.component

import com.dvinc.core.di.provider.ApplicationProvider
import com.dvinc.notepad.di.module.AssistedInjectModule
import com.dvinc.notepad.di.module.NotepadModule
import com.dvinc.notepad.ui.note.NoteFragment
import com.dvinc.notepad.ui.notepad.NotepadFragment
import dagger.Component

@Component(
    dependencies = [
        ApplicationProvider::class
    ],
    modules = [
        NotepadModule::class,
        AssistedInjectModule::class
    ]
)
interface NotepadComponent {

    fun inject(target: NotepadFragment)

    fun inject(target: NoteFragment)

    @Component.Factory
    interface Factory {
        fun create(applicationProvider: ApplicationProvider): NotepadComponent
    }
}
