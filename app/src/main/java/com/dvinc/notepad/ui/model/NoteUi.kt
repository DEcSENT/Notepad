/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.model

data class NoteUi(var id: Long,
                  val name: String,
                  val content: String,
                  val updateTime: String,
                  val markerType: MarkerTypeUi)
