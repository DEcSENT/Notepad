package com.dvinc.notepad.data.repository

import com.dvinc.notepad.data.database.entity.MarkerTypeEntity
import com.dvinc.notepad.data.mapper.NoteDataMapper
import com.dvinc.notepad.domain.model.MarkerType
import org.junit.Test

import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MarkerDataRepositoryTest {

    @Mock
    private lateinit var noteMapper: NoteDataMapper

    private lateinit var markerRepository: MarkerDataRepository

    private lateinit var markerEntityList: List<MarkerTypeEntity>

    private lateinit var markerList: List<MarkerType>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        markerRepository = MarkerDataRepository(noteMapper)
        markerEntityList = MarkerTypeEntity.values().toList()
        markerList = MarkerType.values().toList()

        `when`(noteMapper.mapMarkerType(markerEntityList)).thenReturn(markerList)
    }

    @Test
    fun `mapper was called one time`() {
        markerRepository.getMarkers().subscribe()
        verify(noteMapper, times(1)).mapMarkerType(markerEntityList)
    }

    @Test
    fun `mapper return correct result`() {
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
