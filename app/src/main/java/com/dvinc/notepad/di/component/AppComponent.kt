/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.di.component

import com.dvinc.notepad.di.module.AppModule
import com.dvinc.notepad.di.module.DataModule
import com.dvinc.notepad.presentation.ui.note.NoteFragment
import com.dvinc.notepad.presentation.ui.notepad.NotepadFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    DataModule::class])
interface AppComponent {
    fun inject(notepadFragment: NotepadFragment)
    fun inject(noteFragment: NoteFragment)
}
