/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.domain.usecase.archive

import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.repository.ArchiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArchiveUseCase @Inject constructor(
    private val archiveRepository: ArchiveRepository
) {

    fun getArchivedNotes(): Flow<List<Note>> {
        return archiveRepository.getArchivedNotes()
    }
}
