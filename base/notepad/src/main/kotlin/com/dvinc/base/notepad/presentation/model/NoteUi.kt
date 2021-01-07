/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.base.notepad.presentation.model

data class NoteUi(
    val id: Long,
    val name: String,
    val content: String,
    val updateTime: String
)
