/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.repository.note

import com.dvinc.notepad.data.database.dao.note.NoteDao
import com.dvinc.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.notepad.domain.model.note.Note
import com.dvinc.notepad.domain.repository.note.NoteRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class NoteDataRepository
@Inject constructor(
        private val noteDao: NoteDao,
        private val noteMapper: NoteDataMapper
) : NoteRepository {

    override fun getNotes(): Flowable<List<Note>> {
        return noteDao.getNotes()
                .map { noteMapper.fromEntityToDomain(it) }
    }

    override fun addNote(note: Note): Completable {
        return Single.just(noteMapper.fromDomainToEntity(note))
                .flatMapCompletable {
                    Completable.fromAction { noteDao.addNote(it) }
                }
    }

    override fun deleteNoteById(id: Long): Completable {
        return Completable.fromAction { noteDao.deleteNoteById(id) }
    }

    override fun updateNote(note: Note): Completable {
        return Single.just(noteMapper.fromDomainToEntity(note))
                .flatMapCompletable {
                    Completable.fromAction { noteDao.updateNote(it) }
                }
    }

    override fun getNoteById(id: Long): Single<Note> {
        return Single.fromCallable { noteDao.getNoteById(id) }
                .map { noteMapper.fromEntityToDomain(it) }
    }
}
