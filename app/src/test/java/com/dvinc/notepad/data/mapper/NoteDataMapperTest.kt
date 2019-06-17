package com.dvinc.notepad.data.mapper

import com.dvinc.notepad.data.database.entity.marker.MarkerTypeEntity
import com.dvinc.notepad.data.database.entity.note.NoteEntity
import com.dvinc.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.notepad.domain.model.marker.MarkerType
import com.dvinc.notepad.domain.model.note.Note
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NoteDataMapperTest {

    private lateinit var noteMapper: NoteDataMapper

    private val id = 150L

    private val name = "Name"

    private val content = "Content"

    private val time = 100L

    private val markerTypeEntity = MarkerTypeEntity.IDEA

    private val markerTypeDomain = MarkerType.IDEA

    private val noteEntity = NoteEntity(id, name, content, time, markerTypeEntity)

    private val note = Note(id, name, content, time, markerTypeDomain)

    private var entityList = listOf(noteEntity, noteEntity)

    private val markerTypeEntityList = MarkerTypeEntity.values().toList()

    private val markerTypeDomainList = MarkerType.values().toList()

    @Before
    fun setUp() {
        noteMapper = NoteDataMapper()
    }

    @Test
    fun `correct mapped entities list size`() {
        assert(noteMapper.fromEntityToDomain(entityList).size == entityList.size)
    }

    @Test
    fun `correct map from entity to domain`() {
        val domainModel = noteMapper.fromEntityToDomain(noteEntity)
        assert(domainModel.name == name)
        assert(domainModel.content == content)
        assert(domainModel.updateTime == time)
        assert(domainModel.markerType == markerTypeDomain)
    }

    @Test
    fun `correct map from domain to entity`() {
        val entity = noteMapper.fromDomainToEntity(note)
        assert(entity.name == name)
        assert(entity.content == content)
        assert(entity.updateTime == time)
        assert(entity.markerType == markerTypeEntity)
    }

    @Test
    fun `correct mapped marker type list size`() {
        assert(noteMapper.mapMarkerType(markerTypeEntityList).size == markerTypeDomainList.size)
    }

    @Test
    fun `correct mapped marker type values from entity to domain`() {
        val mappedValue = noteMapper.mapMarkerType(markerTypeEntityList)
        assertEquals(mappedValue, markerTypeDomainList)
    }

    @Test
    fun `correct mapped marker type values from domain to entity`() {
        (0 until markerTypeDomainList.size).forEach {
            val newNote = note.copy(markerType = markerTypeDomainList[it])
            val entity = noteMapper.fromDomainToEntity(newNote)
            assert(entity.markerType == markerTypeEntityList[it])
        }
    }
}
