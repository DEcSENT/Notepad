package com.dvinc.notepad.data.repository

import com.dvinc.core.database.dao.note.NoteDao
import com.dvinc.core.database.entity.note.NoteEntity
import com.dvinc.notepad.CoroutinesTest
import com.dvinc.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.notepad.data.repository.note.NoteDataRepository
import com.dvinc.notepad.domain.model.note.Note
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test

class NoteDataRepositoryTest : CoroutinesTest() {

    private lateinit var noteRepository: NoteDataRepository

    private var noteEntity: NoteEntity = mock()

    private var note: Note = mock()

    private var entityList: List<NoteEntity> = listOf(noteEntity)

    private var noteList: List<Note> = listOf(note)

    private var noteDao: NoteDao = mock()

    private var noteMapper: NoteDataMapper = mock()

    @Before
    fun setUp() {
        noteRepository = NoteDataRepository(noteDao, noteMapper)
    }

    @Test
    fun `when dao returned entities list then flow emits notes list`() = runCoroutineTest {
        // Given
        whenever(noteDao.getNotes()).thenReturn(flow { emit(entityList) })
        whenever(noteMapper.fromEntityToDomain(entityList)).thenReturn(noteList)

        // When
        val flow = noteRepository.getNotes()

        // Then
        flow.collect { flowEmit ->
            assert(flowEmit == noteList)
        }
    }

    @Test
    fun `when add note called then call dao`() = runCoroutineTest {
        // Given
        whenever(noteMapper.fromDomainToEntity(note)).thenReturn(noteEntity)

        // When
        noteRepository.addNote(note)

        // Then
        verify(noteDao).addNote(noteEntity)
    }

    @Test
    fun `when delete note called then call dao with same note id`() = runCoroutineTest {
        // Given
        val noteId = 10L

        // When
        noteRepository.deleteNoteById(noteId)

        // Then
        verify(noteDao).deleteNoteById(noteId)
    }

    @Test
    fun `when get note called then return correct note`() = runCoroutineTest {
        // Given
        val noteId = 10L
        whenever(noteDao.getNoteById(noteId)).thenReturn(noteEntity)
        whenever(noteMapper.fromEntityToDomain(noteEntity)).thenReturn(note)

        // When
        val resultNote = noteRepository.getNoteById(noteId)

        // Then
        assert(resultNote == note)
    }
}