package com.dvinc.notepad.common.snackbar

import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar

object SnackbarFactory {

    fun create(
        mainView: View,
        messageText: String,
        containerResId: Int,
        duration: Int
    ): Snackbar? {

        val viewGroup = mainView.findViewById(containerResId) as ViewGroup?

        return viewGroup?.let {
            Snackbar
                .make(viewGroup, messageText, duration)
        }
    }
}
