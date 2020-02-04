/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.di.component

import com.dvinc.core.di.provider.ApplicationProvider
import com.dvinc.notepad.di.module.AssistedInjectModule
import com.dvinc.notepad.di.module.NotepadModule
import com.dvinc.notepad.presentation.ui.note.NoteFragment
import com.dvinc.notepad.presentation.ui.notepad.NotepadFragment
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

    class Builder private constructor() {

        companion object {

            fun build(applicationProvider: ApplicationProvider): NotepadComponent {
                return DaggerNotepadComponent.builder()
                    .applicationProvider(applicationProvider)
                    .build()
            }
        }
    }
}
