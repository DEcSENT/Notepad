/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/6/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.ui.base

interface MessageView {

    fun showError(errorMessage: String)

    fun showMessage(message: String)
}
 