/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.adapter.item

import androidx.core.content.ContextCompat
import com.dvinc.notepad.R
import com.dvinc.notepad.presentation.model.NoteUi
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_note.ivNoteTypeIcon as markerIcon
import kotlinx.android.synthetic.main.item_note.tvNoteContent as content
import kotlinx.android.synthetic.main.item_note.tvNoteName as name
import kotlinx.android.synthetic.main.item_note.tvNoteTypeText as markerText
import kotlinx.android.synthetic.main.item_note.tvNoteUpdatedTime as updateTime

class NoteItem(
        val note: NoteUi
) : Item() {

    override fun getId(): Long = note.id

    override fun getLayout() = R.layout.item_note

    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            name.text = note.name
            content.text = note.content
            updateTime.text = note.updateTime
            markerIcon.drawable.mutate().setColorFilter(
                ContextCompat.getColor(
                            itemView.context,
                            note.markerType.markerColor),
                    android.graphics.PorterDuff.Mode.MULTIPLY)
            markerText.text = itemView.context.getText(note.markerType.markerName)
        }
    }
}
