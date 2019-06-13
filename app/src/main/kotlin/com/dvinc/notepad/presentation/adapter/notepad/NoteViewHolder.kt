/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.adapter.notepad

import android.view.View
import androidx.core.content.ContextCompat
import com.dvinc.notepad.presentation.model.NoteUi
import com.dvinc.notepad.presentation.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_note.item_note_content as content
import kotlinx.android.synthetic.main.item_note.item_note_name as name
import kotlinx.android.synthetic.main.item_note.item_note_type_icon as markerIcon
import kotlinx.android.synthetic.main.item_note.item_note_type_name as markerText
import kotlinx.android.synthetic.main.item_note.item_note_updating_time as updateTime

class NoteViewHolder(
    itemView: View,
    private val itemClickListener: NotepadAdapter.ItemClickListener?
) : BaseViewHolder(itemView) {

    fun bind(note: NoteUi) {
        name.text = note.name
        content.text = note.content
        updateTime.text = note.updateTime
        markerIcon.drawable.mutate().setColorFilter(
            ContextCompat.getColor(itemView.context, note.markerType.markerColor),
            android.graphics.PorterDuff.Mode.MULTIPLY
        )
        markerText.text = itemView.context.getText(note.markerType.markerName)

        itemView.setOnClickListener {
            itemClickListener?.onItemClick(note)
        }
    }
}
