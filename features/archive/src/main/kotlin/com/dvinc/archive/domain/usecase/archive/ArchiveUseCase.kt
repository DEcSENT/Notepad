/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.archive.domain.usecase.archive

import com.dvinc.archive.domain.repository.ArchiveRepository
import com.dvinc.base.notepad.domain.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArchiveUseCase @Inject constructor(
    private val archiveRepository: ArchiveRepository
) {

    fun getArchivedNotes(): Flow<List<Note>> {
        return archiveRepository.getArchivedNotes()
    }
}
