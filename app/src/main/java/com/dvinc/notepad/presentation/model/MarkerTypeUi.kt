/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.model

import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import com.dvinc.notepad.R

enum class MarkerTypeUi(
        @ColorRes val markerColor: Int,
        @StringRes val markerName: Int) {

    NOTE(R.color.markerNoteType, R.string.marker_note_type),

    CRITICAL(R.color.markerCriticalType, R.string.marker_critical_type),

    TODO(R.color.markerTodoType, R.string.marker_todo_type),

    IDEA(R.color.markerIdeaType, R.string.marker_idea_type)
}
