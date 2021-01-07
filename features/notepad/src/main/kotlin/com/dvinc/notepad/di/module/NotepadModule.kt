/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.di.module

import com.dvinc.core.database.NotepadDatabase
import com.dvinc.core.database.dao.note.NoteDao
import com.dvinc.base.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.notepad.data.repository.note.NoteDataRepository
import com.dvinc.notepad.domain.repository.note.NoteRepository
import dagger.Module
import dagger.Provides

@Module
class NotepadModule {

    // TODO(dv): create base DI component for this?
    @Provides
    fun provideNoteDao(notepadDatabase: NotepadDatabase): NoteDao {
        return notepadDatabase.notesDao()
    }

    @Provides
    fun provideNoteRepository(
        noteDao: NoteDao,
        noteDataMapper: NoteDataMapper
    ): NoteRepository {
        return NoteDataRepository(noteDao, noteDataMapper)
    }
}
