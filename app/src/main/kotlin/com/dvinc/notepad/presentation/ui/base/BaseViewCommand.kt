/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.base

data class ShowMessage(val messageResId: Int) : ViewCommand

data class ShowErrorMessage(val messageResId: Int) : ViewCommand
