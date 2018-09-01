package com.dvinc.notepad.data.database.converter

import com.dvinc.notepad.data.database.entity.marker.MarkerTypeEntity
import org.junit.Test

class MarkerTypeConverterTest {

    private val markerTypeEntityList = MarkerTypeEntity.values().toList()

    @Test
    fun fromType() {
        (0 until markerTypeEntityList.size).forEach {
            assert(MarkerTypeConverter.fromType(markerTypeEntityList[0]) == markerTypeEntityList[0].name)
        }
    }

    @Test
    fun toType() {
        (0 until markerTypeEntityList.size).forEach {
            assert(MarkerTypeConverter.toType(markerTypeEntityList[0].name) == markerTypeEntityList[0])
        }
    }
}
