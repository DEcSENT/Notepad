/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/14/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.data.repository.marker

import com.dvinc.notepad.data.database.entity.marker.MarkerTypeEntity
import com.dvinc.notepad.data.mapper.note.NoteDataMapper
import com.dvinc.notepad.domain.model.marker.MarkerType
import com.dvinc.notepad.domain.repository.marker.MarkerRepository
import io.reactivex.Single
import javax.inject.Inject

class MarkerDataRepository @Inject constructor(
        private val noteMapper: NoteDataMapper
) : MarkerRepository {

    override fun getMarkers(): Single<List<MarkerType>> {
        return Single.just(obtainMarkers())
                .map { noteMapper.mapMarkerType(it) }
    }

    private fun obtainMarkers(): List<MarkerTypeEntity> {
        return MarkerTypeEntity.values().toList()
    }
}
