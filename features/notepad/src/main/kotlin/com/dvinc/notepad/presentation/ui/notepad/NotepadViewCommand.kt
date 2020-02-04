/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.notepad

import com.dvinc.core.ui.ViewCommand

data class OpenNoteScreen(val noteId: Long) : ViewCommand
