/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.di.modules

import com.dvinc.notepad.data.repository.NotesRepository
import com.dvinc.notepad.domain.interactors.NotesInteractor
import com.dvinc.notepad.domain.interactors.NotesInteractorImpl
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun provideNotesInteractor(
            repository: NotesRepository
    ): NotesInteractor = NotesInteractorImpl(repository)
}
 