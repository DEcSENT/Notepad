package com.dvinc.notepad.data.repository

import com.dvinc.notepad.data.database.entity.marker.MarkerTypeEntity
import com.dvinc.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.notepad.data.repository.marker.MarkerDataRepository
import com.dvinc.notepad.domain.model.marker.MarkerType
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class MarkerDataRepositoryTest {

    private lateinit var markerRepository: MarkerDataRepository

    private var markerEntityList: List<MarkerTypeEntity> = MarkerTypeEntity.values().toList()

    private var markerList: List<MarkerType> = MarkerType.values().toList()

    private var noteMapper: NoteDataMapper = mock() {
        on { mapMarkerType(markerEntityList) }.doReturn(markerList)
    }

    @Before
    fun setUp() {
        markerRepository = MarkerDataRepository(noteMapper)
    }

    @Test
    fun `mapper was called one time`() {
        markerRepository.getMarkers().subscribe()
        verify(noteMapper, times(1)).mapMarkerType(markerEntityList)
    }

    @Test
    fun `mapper returned correct result`() {
        markerRepository.getMarkers()
            .test()
            .assertValue(markerList)
    }

    @Test
    fun `getMarkers was called without error`() {
        markerRepository.getMarkers()
            .test()
            .assertNoErrors()
    }
}
