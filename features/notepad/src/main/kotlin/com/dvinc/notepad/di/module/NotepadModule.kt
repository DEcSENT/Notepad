/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.di.module

import com.dvinc.core.database.NotepadDatabase
import com.dvinc.core.database.dao.note.NoteDao
import com.dvinc.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.notepad.data.repository.archive.ArchiveDataRepository
import com.dvinc.notepad.data.repository.note.NoteDataRepository
import com.dvinc.notepad.domain.repository.ArchiveRepository
import com.dvinc.notepad.domain.repository.note.NoteRepository
import dagger.Module
import dagger.Provides

@Module
class NotepadModule {

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

    @Provides
    fun provideArchiveRepository(
        noteDao: NoteDao,
        noteDataMapper: NoteDataMapper
    ): ArchiveRepository {
        return ArchiveDataRepository(noteDao, noteDataMapper)
    }
}
