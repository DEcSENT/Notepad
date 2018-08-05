/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.adapters

import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dvinc.notepad.R
import com.dvinc.notepad.ui.model.NoteUi

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var notes: List<NoteUi>? = null
    private var noteClickListener: (noteId: Long) -> Unit = {}

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val note = notes?.get(position)
            name?.text = note?.name
            content?.text = note?.content
            updateTime?.text = note?.updateTime
            //TODO: Refactor this
            markerIcon?.drawable?.mutate()?.setColorFilter(itemView.context.resources.getColor(
                    note?.markerType?.markerColor ?: 0),
                    PorterDuff.Mode.MULTIPLY)
            markerText?.text = itemView.context.getText(note?.markerType?.markerName ?: 0)
            itemView.setOnClickListener { noteClickListener.invoke(note?.id ?: 0) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return notes?.size ?: 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tvNoteName)
        val content = itemView.findViewById<TextView>(R.id.tvNoteContent)
        val updateTime = itemView.findViewById<TextView>(R.id.tvNoteUpdatedTime)
        val markerIcon = itemView.findViewById<ImageView>(R.id.ivNoteTypeIcon)
        val markerText = itemView.findViewById<TextView>(R.id.tvNoteTypeText)
    }

    fun setNotes(notes: List<NoteUi>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNote(position: Int) = notes?.get(position)

    fun setOnNoteClickListener(listener: (noteId: Long) -> Unit) {
        noteClickListener = listener
    }
}
