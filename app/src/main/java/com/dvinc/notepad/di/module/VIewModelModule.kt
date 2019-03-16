/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.di.module

import androidx.lifecycle.ViewModel
import com.dvinc.notepad.di.annotation.ViewModelKey
import com.dvinc.notepad.presentation.ui.notepad.NotepadViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NotepadViewModel::class)
    abstract fun provideExerciseViewModel(viewModel: NotepadViewModel): ViewModel
}
