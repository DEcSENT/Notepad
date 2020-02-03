package com.dvinc.core.snackbar

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.dvinc.core.R
import com.google.android.material.snackbar.Snackbar

object SnackbarFactory {

    fun create(
        mainView: View,
        messageText: String,
        containerResId: Int,
        duration: Int,
        @ColorRes backgroundColor: Int,
        @ColorRes textColor: Int
    ): Snackbar? {

        val viewGroup = mainView.findViewById(containerResId) as? ViewGroup

        return viewGroup?.let {
            Snackbar
                .make(viewGroup, messageText, duration)
                .decorate(backgroundColor, textColor)
        }
    }

    private fun Snackbar.decorate(@ColorRes backgroundId: Int, @ColorRes textColorId: Int): Snackbar {
        val layout = view as? Snackbar.SnackbarLayout ?: return this

        val textView = with(layout) {
            layout.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(view.context, backgroundId))
            findViewById<TextView>(R.id.snackbar_text)
        }

        textView.setTextColor(ContextCompat.getColor(view.context, textColorId))

        return this
    }
}
