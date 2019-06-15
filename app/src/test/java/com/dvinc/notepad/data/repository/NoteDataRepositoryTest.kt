package com.dvinc.notepad.data.repository

import com.dvinc.notepad.data.database.dao.note.NoteDao
import com.dvinc.notepad.data.database.entity.note.NoteEntity
import com.dvinc.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.notepad.data.repository.note.NoteDataRepository
import com.dvinc.notepad.domain.model.note.Note
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class NoteDataRepositoryTest {

    private lateinit var repository: NoteDataRepository

    private var noteEntity: NoteEntity = mock()

    private var note: Note = mock()

    private var entityList: List<NoteEntity> = listOf(noteEntity)

    private var noteList: List<Note> = listOf(note)

    private val testNoteId = 100L

    private var noteDao: NoteDao = mock() {
        on { getNoteById(testNoteId) }.doReturn(noteEntity)
        on { getNotes() }.doReturn(Flowable.just(entityList))
    }

    private var noteMapper: NoteDataMapper = mock() {
        on { fromEntityToDomain(noteEntity) }.doReturn(note)
        on { fromDomainToEntity(note) }.doReturn(noteEntity)
        on { fromEntityToDomain(entityList) }.doReturn(noteList)
    }

    @Before
    fun setUp() {
        repository = NoteDataRepository(noteDao, noteMapper)
    }

    @Test
    fun getNotes() {
        repository.getNotes()
                .test()
                .assertNoErrors()
                .assertValue(noteList)
    }

    @Test
    fun `verify noteMapper was called once when noteDao returned notes list`() {
        // Given

        // When
        repository.getNotes().test()

        // Then
        verify(noteMapper, times(1)).fromEntityToDomain(entityList)
    }

    @Test
    fun addNote() {
        repository.addNote(note)
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun `verify noteMapper was called once when adding new note`() {
        // Given

        // When
        repository.addNote(note).test()

        // Then
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
    fun `verify noteDao was called when deleting note by id`() {
        // Given
        val noteId = 10L

        // When
        repository.deleteNoteById(noteId).test()

        // Then
        verify(noteDao, times(1)).deleteNoteById(noteId)
    }

    @Test
    fun getNoteById() {
        repository.getNoteById(testNoteId)
                .test()
                .assertNoErrors()
                .assertValue(note)
    }

    @Test
    fun `verify noteMapper and noteDao were called once when requesting note by id`() {
        // Given

        // When
        repository.getNoteById(testNoteId).test()

        // Then
        verify(noteDao, times(1)).getNoteById(testNoteId)
        verify(noteMapper, times(1)).fromEntityToDomain(noteEntity)
    }
}
