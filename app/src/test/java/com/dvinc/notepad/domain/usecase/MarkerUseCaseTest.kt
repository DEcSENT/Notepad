package com.dvinc.notepad.domain.usecase

import com.dvinc.notepad.domain.model.marker.MarkerType
import com.dvinc.notepad.domain.repository.marker.MarkerRepository
import com.dvinc.notepad.domain.usecase.marker.MarkerUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MarkerUseCaseTest {

    @Mock
    private lateinit var markerRepository: MarkerRepository

    private val markerTypeList = MarkerType.values().toList()

    private lateinit var markerUseCase: MarkerUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(markerRepository.getMarkers()).thenReturn(Single.just(markerTypeList))

        markerUseCase = MarkerUseCase(markerRepository)
    }

    @Test
    fun getNoteMarkers() {
        markerUseCase.getNoteMarkers()
        verify(markerRepository).getMarkers()
    }

    @Test
    fun `check correct result from getNoteMarkers()`() {
        markerUseCase.getNoteMarkers()
                .test()
                .assertNoErrors()
                .assertResult(markerTypeList)
    }
}
