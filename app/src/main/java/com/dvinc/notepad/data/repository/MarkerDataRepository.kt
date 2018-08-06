/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/14/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.data.repository

import com.dvinc.notepad.data.database.entity.MarkerTypeEntity
import com.dvinc.notepad.data.mapper.NoteDataMapper
import com.dvinc.notepad.domain.model.MarkerType
import com.dvinc.notepad.domain.repository.MarkerRepository
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
