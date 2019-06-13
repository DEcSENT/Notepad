/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.adapter.notepad

import androidx.recyclerview.widget.DiffUtil
import com.dvinc.notepad.presentation.model.NoteUi

class NotepadDiffUtilsCallback(
    private val oldNotesList: List<NoteUi>,
    private val newNotesList: List<NoteUi>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNotesList[oldItemPosition].id == newNotesList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNotesList[oldItemPosition].hashCode() == newNotesList[newItemPosition].hashCode()
    }

    override fun getOldListSize(): Int {
        return oldNotesList.size
    }

    override fun getNewListSize(): Int {
        return newNotesList.size
    }
}
