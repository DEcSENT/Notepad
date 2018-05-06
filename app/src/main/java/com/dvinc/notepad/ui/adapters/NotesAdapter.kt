/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.adapters

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dvinc.notepad.R
import com.dvinc.notepad.domain.model.Note

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var notes: List<Note>? = null
    private var noteClickListener: (noteId: Long) -> Unit = {}

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes?.get(position)
        holder.name?.text = note?.name
        holder.content?.text = note?.content
        holder.updateTime?.text = note?.updateTime
        holder.markerIcon?.drawable?.mutate()?.
                setColorFilter(Color.parseColor(note?.markerColor), PorterDuff.Mode.MULTIPLY)
        holder.markerText?.text = note?.markerText

        holder.itemView.setOnClickListener { noteClickListener.invoke(note?.id ?: 0) }
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

    fun setNotes(notes : List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNote(position: Int) = notes?.get(position)

    fun setOnNoteClickListener(listener: (noteId: Long) -> Unit) {
        noteClickListener = listener
    }
}
