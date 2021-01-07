/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.di.module

import com.dvinc.core.database.NotepadDatabase
import com.dvinc.core.database.dao.note.NoteDao
import com.dvinc.base.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.core.database.dao.archive.ArchiveDao
import com.dvinc.notepad.data.repository.notepad.NotepadDataRepository
import com.dvinc.notepad.domain.repository.notepad.NotepadRepository
import dagger.Module
import dagger.Provides

@Module
class NotepadModule {

    @Provides
    fun provideNoteDao(notepadDatabase: NotepadDatabase): NoteDao {
        return notepadDatabase.notesDao()
    }

    @Provides
    fun provideArchiveDao(notepadDatabase: NotepadDatabase): ArchiveDao {
        return notepadDatabase.archiveDao()
    }

    @Provides
    fun provideNoteRepository(
        noteDao: NoteDao,
        archiveDao: ArchiveDao,
        noteDataMapper: NoteDataMapper
    ): NotepadRepository {
        return NotepadDataRepository(noteDao, archiveDao, noteDataMapper)
    }
}
