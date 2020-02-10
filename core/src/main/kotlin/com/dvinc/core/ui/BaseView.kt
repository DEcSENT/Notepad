/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.core.ui

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

interface BaseView {

    fun showMessage(
        @StringRes messageResId: Int,
        containerResId: Int = android.R.id.content,
        anchorView: View? = null,
        duration: Int = Snackbar.LENGTH_LONG
    )

    fun showErrorMessage(
        @StringRes messageResId: Int,
        containerResId: Int = android.R.id.content,
        anchorView: View? = null,
        duration: Int = Snackbar.LENGTH_LONG
    )
}
