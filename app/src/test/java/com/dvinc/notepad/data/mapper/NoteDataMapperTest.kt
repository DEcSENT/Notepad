package com.dvinc.notepad.data.mapper

import com.dvinc.notepad.data.database.entity.note.NoteEntity
import com.dvinc.notepad.data.mapper.note.NoteDataMapper
import org.junit.Before
import org.junit.Test

class NoteDataMapperTest {

    private lateinit var noteMapper: NoteDataMapper

    private val id = 150L

    private val name = "Name"

    private val content = "Content"

    private val time = 100L

    private val noteEntity = NoteEntity(id, name, content, time)

    private var entityList = listOf(noteEntity, noteEntity)

    @Before
    fun setUp() {
        noteMapper = NoteDataMapper()
    }

    @Test
    fun `correct mapped entities list size`() {
        assert(noteMapper.fromEntityToDomain(entityList).size == entityList.size)
    }
}
