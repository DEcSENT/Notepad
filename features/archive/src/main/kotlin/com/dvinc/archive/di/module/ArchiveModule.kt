package com.dvinc.archive.di.module

import com.dvinc.archive.data.repository.archive.ArchiveDataRepository
import com.dvinc.archive.domain.repository.ArchiveRepository
import com.dvinc.base.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.core.database.NotepadDatabase
import com.dvinc.core.database.dao.archive.ArchiveDao
import dagger.Module
import dagger.Provides

@Module
class ArchiveModule {

    @Provides
    fun provideArchiveDao(notepadDatabase: NotepadDatabase): ArchiveDao {
        return notepadDatabase.archiveDao()
    }

    @Provides
    fun provideArchiveRepository(
        archiveDao: ArchiveDao,
        noteDataMapper: NoteDataMapper
    ): ArchiveRepository {
        return ArchiveDataRepository(archiveDao, noteDataMapper)
    }
}
