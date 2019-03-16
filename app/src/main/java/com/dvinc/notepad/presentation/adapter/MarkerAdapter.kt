/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/5/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.adapter

import android.content.Context
import android.graphics.PorterDuff
import androidx.core.content.ContextCompat
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dvinc.notepad.R
import com.dvinc.notepad.presentation.model.MarkerTypeUi

class MarkerAdapter(
        private val cntxt: Context?,
        private val layoutId: Int,
        private val markers: List<MarkerTypeUi>
) : ArrayAdapter<MarkerTypeUi>(cntxt, layoutId, markers) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createItemView(position, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createItemView(position, parent)
    }

    private fun createItemView(position: Int, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(layoutId, parent, false)

        val marker = markers[position]

        //Kotlin extension doesn't work here, don't know why.
        val markerIcon = view.findViewById<ImageView>(R.id.item_marker_icon)
        val markerText = view.findViewById<TextView>(R.id.item_marker_text)

        cntxt?.let {
            markerIcon.drawable.mutate().setColorFilter(
                    ContextCompat.getColor(it, marker.markerColor),
                    PorterDuff.Mode.MULTIPLY)
            markerText.text = it.getString(marker.markerName)
        }

        return view
    }
}
 