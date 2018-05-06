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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name?.text = notes?.get(position)?.name
        holder.content?.text = notes?.get(position)?.content
        holder.updateTime?.text = notes?.get(position)?.updateTime
        holder.markerIcon?.drawable?.mutate()?.
                setColorFilter(Color.parseColor(notes?.get(position)?.markerColor), PorterDuff.Mode.MULTIPLY)
        holder.markerText?.text = notes?.get(position)?.markerText
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
}
