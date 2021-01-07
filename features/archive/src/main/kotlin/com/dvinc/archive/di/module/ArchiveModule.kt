package com.dvinc.archive.di.module

import com.dvinc.archive.data.repository.archive.ArchiveDataRepository
import com.dvinc.archive.domain.repository.ArchiveRepository
import com.dvinc.base.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.core.database.NotepadDatabase
import com.dvinc.core.database.dao.note.NoteDao
import dagger.Module
import dagger.Provides

@Module
class ArchiveModule {

    // TODO(dv): create base DI component for this?
    @Provides
    fun provideNoteDao(notepadDatabase: NotepadDatabase): NoteDao {
        return notepadDatabase.notesDao()
    }

    @Provides
    fun provideArchiveRepository(
        noteDao: NoteDao,
        noteDataMapper: NoteDataMapper
    ): ArchiveRepository {
        return ArchiveDataRepository(noteDao, noteDataMapper)
    }
}
