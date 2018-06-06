package com.dvinc.notepad.domain.mappers

import com.dvinc.notepad.domain.model.NoteMarker
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NoteMapperTest {

    private val mapper: NoteMapper = NoteMapper()
    private val name = "Test"
    private val content = "Content"
    private val time = 0L
    private val markerId = 1
    private val id = 150L
    private val testMarkerName = "Marker"
    private val testMarkerColor = "FFFFFF"
    private val testMarker = NoteMarker(markerId, testMarkerName, testMarkerColor)
    private val markerList = listOf(testMarker, testMarker)

    @Test
    fun mapEntitiesToNotes() {
        val entity = mapper.createEntity(name, content, time, markerId, id)
        val note = mapper.mapEntityToNote(entity, markerList)

        assertEquals(id, note.id)
        assertEquals(name, note.name)
        assertEquals(content, note.content)
        //TODO: Check time here
        assertEquals(markerId, note.markerId)
        assertEquals(testMarkerColor, note.markerColor)
        assertEquals(testMarkerName, note.markerText)
    }

    @Test
    fun mapEntityToNote() {
        val testNoteEntity = mapper.createEntity(name, content, time, markerId, id)

        assertEquals(name, testNoteEntity.name)
        assertEquals(content, testNoteEntity.content)
        assertEquals(time, testNoteEntity.updateTime)
        assertEquals(markerId, testNoteEntity.markerId)
        assertEquals(id, testNoteEntity.id)
    }

    @Test
    fun createEntity() {
        //TODO: create test
    }
}