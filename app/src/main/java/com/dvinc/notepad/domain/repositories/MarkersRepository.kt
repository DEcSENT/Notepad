/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 6/8/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.repositories

import com.dvinc.notepad.domain.model.NoteMarker
import io.reactivex.Single

interface MarkersRepository {

    fun getMarkers(): Single<List<NoteMarker>>

    //TODO: This is non-reactive duplicate, fix it
    fun obtainMarkers(): List<NoteMarker>
}
