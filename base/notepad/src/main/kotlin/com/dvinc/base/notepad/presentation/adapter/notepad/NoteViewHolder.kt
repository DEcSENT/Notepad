/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.base.notepad.presentation.adapter.notepad

import android.view.View
import com.dvinc.core.ui.BaseViewHolder
import com.dvinc.base.notepad.presentation.model.NoteUi
import kotlinx.android.synthetic.main.item_note.item_note_content as content
import kotlinx.android.synthetic.main.item_note.item_note_name as name
import kotlinx.android.synthetic.main.item_note.item_note_updating_time as updateTime

class NoteViewHolder(
    itemView: View,
    private val itemClickListener: NotepadAdapter.ItemClickListener?
) : BaseViewHolder(itemView) {

    fun bind(note: NoteUi) {
        name.text = note.name
        content.text = note.content
        updateTime.text = note.updateTime
        itemView.setOnClickListener {
            itemClickListener?.onItemClick(note)
        }
    }
}
