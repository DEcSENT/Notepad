/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.di.components

import com.dvinc.notepad.di.modules.AppModule
import com.dvinc.notepad.di.modules.InteractorModule
import com.dvinc.notepad.ui.note.NoteFragment
import com.dvinc.notepad.ui.notepad.NotepadFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, InteractorModule::class))
interface AppComponent {
    fun inject(notepadFragment: NotepadFragment)
    fun inject(noteFragment: NoteFragment)
}
