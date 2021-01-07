/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/2/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.base.notepad.domain.model

data class Note(
    val id: Long,
    val name: String,
    val content: String,
    val updateTime: Long
)
