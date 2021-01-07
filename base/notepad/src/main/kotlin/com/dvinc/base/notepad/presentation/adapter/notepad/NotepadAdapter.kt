/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.base.notepad.presentation.adapter.notepad

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dvinc.notepad.R
import com.dvinc.base.notepad.presentation.model.NoteUi

class NotepadAdapter : RecyclerView.Adapter<NoteViewHolder>() {

    private var notes: List<NoteUi> = emptyList()

    private var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val context = parent.context
        val noteItemLayoutId = R.layout.item_note
        val view = LayoutInflater.from(context).inflate(noteItemLayoutId, parent, false)
        return NoteViewHolder(view, itemClickListener)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    fun setOnItemClickListener(listener: ItemClickListener) {
        itemClickListener = listener
    }

    fun updateNotes(newNotes: List<NoteUi>) {
        val diffUtilCallback = NotepadDiffUtilsCallback(notes, newNotes)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback, true)
        diffUtilResult.dispatchUpdatesTo(this)
        notes = newNotes
    }

    fun getItem(position: Int): NoteUi {
        return notes[position]
    }

    interface ItemClickListener {

        fun onItemClick(note: NoteUi)
    }
}
