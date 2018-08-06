package com.dvinc.notepad.data.mapper

import com.dvinc.notepad.data.database.entity.MarkerTypeEntity
import com.dvinc.notepad.data.database.entity.NoteEntity
import com.dvinc.notepad.domain.model.MarkerType
import com.dvinc.notepad.domain.model.Note
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
        assert(noteMapper.fromEntityToDomain(entityList).size == 2)
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
    fun `correct mapped marker type list`() {
        assert(noteMapper.mapMarkerType(markerTypeEntityList).size == markerTypeDomainList.size)
    }

    //Looks like dangerous test. Need to think about it later
    @Test
    fun `correct mapped marker type values from entity to domain`() {
        val mappedValue = noteMapper.mapMarkerType(markerTypeEntityList)
        (0 until markerTypeEntityList.size).forEach {
            assert(mappedValue[it] == markerTypeDomainList[it])
        }
    }

    //Looks like dangerous test. Need to think about it later
    @Test
    fun `correct mapped marker type values from domain to entity`() {
        (0 until markerTypeDomainList.size).forEach {
            val newNote = note.copy(markerType = markerTypeDomainList[it])
            val entity = noteMapper.fromDomainToEntity(newNote)
            assert(entity.markerType == markerTypeEntityList[it])
        }
    }
}
