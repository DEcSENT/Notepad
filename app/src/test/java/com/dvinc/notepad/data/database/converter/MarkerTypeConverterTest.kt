package com.dvinc.notepad.data.database.converter

import com.dvinc.notepad.data.database.entity.marker.MarkerTypeEntity
import org.junit.Test

class MarkerTypeConverterTest {

    private val markerTypeEntityList = MarkerTypeEntity.values().toList()

    @Test
    fun `assert that markerTypes was converted from type right`() {
        (0 until markerTypeEntityList.size).forEach {
            assert(MarkerTypeConverter.fromType(markerTypeEntityList[it]) == markerTypeEntityList[it].name)
        }
    }

    @Test
    fun `assert that markerTypes was converted to type right`() {
        (0 until markerTypeEntityList.size).forEach {
            assert(MarkerTypeConverter.toType(markerTypeEntityList[it].name) == markerTypeEntityList[it])
        }
    }
}
