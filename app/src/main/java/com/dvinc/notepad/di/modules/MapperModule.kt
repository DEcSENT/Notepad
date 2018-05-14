/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/14/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.di.modules

import com.dvinc.notepad.domain.mappers.NoteMapper
import dagger.Module
import dagger.Provides

@Module
class MapperModule {

    @Provides
    fun provideNoteMapper(): NoteMapper = NoteMapper()
}
 