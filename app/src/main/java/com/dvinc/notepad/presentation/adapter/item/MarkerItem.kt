/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.adapter.item

import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import com.dvinc.notepad.R
import com.dvinc.notepad.presentation.model.MarkerTypeUi
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_note_marker.ivMarkerIcon as markerIcon
import kotlinx.android.synthetic.main.item_note_marker.tvMarkerText as markerText

class MarkerItem(
        val marker: MarkerTypeUi
) : Item() {

    override fun getLayout() = R.layout.item_note_marker

    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            itemView.context.let {
                markerIcon.drawable.mutate().setColorFilter(
                        ContextCompat.getColor(it, marker.markerColor),
                        PorterDuff.Mode.MULTIPLY)
                markerText.text = it.getString(marker.markerName)
            }
        }
    }
}
