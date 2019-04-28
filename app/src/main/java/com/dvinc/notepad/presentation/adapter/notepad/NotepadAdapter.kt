/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.adapter.notepad

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dvinc.notepad.R
import com.dvinc.notepad.presentation.model.NoteUi

class NotepadAdapter : RecyclerView.Adapter<NoteViewHolder>() {

    private var notes: List<NoteUi> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val context = parent.context
        val noteItemLayoutId = R.layout.item_note
        val view = LayoutInflater.from(context).inflate(noteItemLayoutId, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    //todo diffUtil
    fun updateNotes(newNotes: List<NoteUi>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
