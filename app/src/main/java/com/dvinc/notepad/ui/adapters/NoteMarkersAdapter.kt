/*
 * Copyright (c) 2018 by Denis Verentsov
 * Date: 5/5/2018
 * Email: decsent@yandex.ru
 * All rights reserved.
 */

package com.dvinc.notepad.ui.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ArrayAdapter
import com.dvinc.notepad.domain.model.NoteMarker
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dvinc.notepad.R

class NoteMarkersAdapter(context: Context?, resource: Int, objects: List<NoteMarker>) : ArrayAdapter<NoteMarker>(context, resource, objects) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val markers: List<NoteMarker> = objects
    private val layoutId: Int = resource

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createItemView(position, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createItemView(position, parent)
    }

    private fun createItemView(position: Int, parent: ViewGroup?): View {
        val view = inflater.inflate(layoutId, parent, false)

        val marker = markers[position]

        //Kotlin extension doesn't work here, don't know why.
        val markerIcon = view.findViewById<ImageView>(R.id.ivMarkerIcon)
        val markerText = view.findViewById<TextView>(R.id.tvMarkerText)

        markerIcon.drawable.mutate().setColorFilter(Color.parseColor(marker.markerColor), PorterDuff.Mode.MULTIPLY)
        markerText.text = marker.markerName

        return view
    }
}
 