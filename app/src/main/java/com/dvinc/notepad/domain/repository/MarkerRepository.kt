/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 6/8/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.domain.repository

import com.dvinc.notepad.domain.model.MarkerType
import io.reactivex.Single

interface MarkerRepository {

    fun getMarkers(): Single<List<MarkerType>>
}
