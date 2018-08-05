/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.repository

import com.dvinc.notepad.data.database.dao.NoteDao
import com.dvinc.notepad.data.database.entity.NoteEntity
import com.dvinc.notepad.domain.repository.NoteRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class NoteDataRepository
@Inject constructor(
        private val noteDao: NoteDao
): NoteRepository {

    override fun getNotes(): Flowable<List<NoteEntity>> {
        return noteDao.getNotes()
    }

    override fun addNote(note: NoteEntity): Completable {
        return Completable.fromAction { noteDao.addNote(note) }
    }

    override fun deleteNoteById(id: Int): Completable {
        return Completable.fromAction { noteDao.deleteNoteById(id) }
    }

    override fun updateNote(note: NoteEntity): Completable {
        return Completable.fromAction { noteDao.updateNote(note) }
    }

    override fun getNoteById(id: Long): Single<NoteEntity> {
        return Single.fromCallable { noteDao.getNoteById(id) }
    }
}
