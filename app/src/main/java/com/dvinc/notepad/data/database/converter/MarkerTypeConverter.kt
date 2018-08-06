/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.data.database.converter

import android.arch.persistence.room.TypeConverter
import com.dvinc.notepad.data.database.entity.MarkerTypeEntity

object MarkerTypeConverter {

    @TypeConverter
    @JvmStatic
    fun fromType(type: MarkerTypeEntity): String = type.name

    @TypeConverter
    @JvmStatic
    fun toType(name: String): MarkerTypeEntity = MarkerTypeEntity.valueOf(name)
}
