package com.dvinc.notepad.data.repository

import com.dvinc.notepad.data.database.dao.NoteDao
import com.dvinc.notepad.data.database.entity.NoteEntity
import com.dvinc.notepad.data.mapper.NoteDataMapper
import com.dvinc.notepad.domain.model.Note
import io.reactivex.Flowable
import org.junit.Test

import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class NoteDataRepositoryTest {

    @Mock
    private lateinit var noteDao: NoteDao

    @Mock
    private lateinit var noteMapper: NoteDataMapper

    @Mock
    private lateinit var noteEntity: NoteEntity

    @Mock
    private lateinit var note: Note

    private lateinit var repository: NoteDataRepository

    private lateinit var entityList: List<NoteEntity>

    private lateinit var noteList: List<Note>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        repository = NoteDataRepository(noteDao, noteMapper)
        entityList = listOf(noteEntity)
        noteList = listOf(note)

        `when`(noteDao.getNoteById(0)).thenReturn(noteEntity)
        `when`(noteDao.getNotes()).thenReturn(Flowable.just(entityList))

        `when`(noteMapper.fromEntityToDomain(noteEntity)).thenReturn(note)
        `when`(noteMapper.fromDomainToEntity(note)).thenReturn(noteEntity)
        `when`(noteMapper.fromEntityToDomain(entityList)).thenReturn(noteList)
    }

    @Test
    fun getNotes() {
        repository.getNotes()
                .test()
                .assertNoErrors()
                .assertValue(noteList)

        verify(noteMapper, times(1)).fromEntityToDomain(entityList)
    }

    @Test
    fun addNote() {
        repository.addNote(note)
                .test()
                .assertNoErrors()
                .assertComplete()

        verify(noteMapper, times(1)).fromDomainToEntity(note)
    }

    @Test
    fun deleteNoteById() {
        repository.deleteNoteById(0)
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun updateNote() {
        repository.updateNote(note)
                .test()
                .assertNoErrors()
                .assertComplete()

        verify(noteMapper, times(1)).fromDomainToEntity(note)
    }

    @Test
    fun getNoteById() {
        repository.getNoteById(0)
                .test()
                .assertNoErrors()
                .assertValue(note)

        verify(noteMapper, times(1)).fromEntityToDomain(noteEntity)
    }
}
