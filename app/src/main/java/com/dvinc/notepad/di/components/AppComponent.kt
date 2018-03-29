/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.di.components

import com.dvinc.notepad.di.modules.AppModule
import com.dvinc.notepad.ui.newnote.NewNoteFragment
import com.dvinc.notepad.ui.notepad.NotepadFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(notepadFragment: NotepadFragment)
    fun inject(newNoteFragment: NewNoteFragment)
}
