package com.dvinc.notepad.data.mapper

import com.dvinc.notepad.data.database.entity.note.NoteEntity
import com.dvinc.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.notepad.domain.model.note.Note
import org.junit.Before
import org.junit.Test

class NoteDataMapperTest {

    private lateinit var noteMapper: NoteDataMapper

    private val id = 150L

    private val name = "Name"

    private val content = "Content"

    private val time = 100L

    private val note = Note(id, name, content, time)

    private val noteEntity = NoteEntity(id, name, content, time)

    private var entityList = listOf(noteEntity, noteEntity)

    @Before
    fun setUp() {
        noteMapper = NoteDataMapper()
    }

    @Test
    fun `check that notes list size is correct after conversion`() {
        assert(noteMapper.fromEntityToDomain(entityList).size == entityList.size)
    }

    @Test
    fun `check that note entity conversion is correct`() {
        // Given

        // When
        val result = noteMapper.fromEntityToDomain(noteEntity)

        // Then
        assert(result == note)
    }

    @Test
    fun `check that note to note entity conversion is correct`() {
        // Given

        // When
        val result = noteMapper.fromDomainToEntity(note)

        // Then
        assert(result == noteEntity)
    }
}
