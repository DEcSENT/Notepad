/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.domain.usecase.marker

import com.dvinc.notepad.domain.model.marker.MarkerType
import com.dvinc.notepad.domain.repository.marker.MarkerRepository
import io.reactivex.Single
import javax.inject.Inject

class MarkerUseCase @Inject constructor(
        private val markerRepository: MarkerRepository
) {

    fun getNoteMarkers(): Single<List<MarkerType>> {
        return markerRepository.getMarkers()
    }
}
