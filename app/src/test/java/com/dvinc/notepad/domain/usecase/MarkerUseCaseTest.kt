package com.dvinc.notepad.domain.usecase

import com.dvinc.notepad.domain.model.marker.MarkerType
import com.dvinc.notepad.domain.repository.marker.MarkerRepository
import com.dvinc.notepad.domain.usecase.marker.MarkerUseCase
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify

class MarkerUseCaseTest {

    private lateinit var markerUseCase: MarkerUseCase

    private val markerTypeList = MarkerType.values().toList()

    private var markerRepository: MarkerRepository = mock() {
        on { getMarkers() }.doReturn(Single.just(markerTypeList))
    }

    @Before
    fun setUp() {
        markerUseCase = MarkerUseCase(markerRepository)
    }

    @Test
    fun `verify markerRepo was called once when retrieving markers`() {
        markerUseCase.getNoteMarkers()
        verify(markerRepository, times(1)).getMarkers()
    }

    @Test
    fun `check correct result from getNoteMarkers()`() {
        markerUseCase.getNoteMarkers()
            .test()
            .assertNoErrors()
            .assertResult(markerTypeList)
    }
}
