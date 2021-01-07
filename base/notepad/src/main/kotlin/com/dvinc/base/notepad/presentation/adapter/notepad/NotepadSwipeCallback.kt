/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.base.notepad.presentation.adapter.notepad

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dvinc.notepad.R
import com.dvinc.base.notepad.presentation.model.NoteUi

class NotepadSwipeCallback(
    private val notesAdapter: NotepadAdapter,
    private val onItemSwipedListener: (note: NoteUi, swipeDirection: NotepadSwipeDirection) -> Unit
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    companion object {
        private const val BACKGROUND_CORNER_OFFSET = 100
    }

    private val deletedItemBackground = ColorDrawable(Color.RED)

    private val archiveItemBackground = ColorDrawable(Color.GREEN)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val swipedNote = notesAdapter.getItem(position)
        // (!) Be careful about directions
        if (direction == ItemTouchHelper.RIGHT) {
            onItemSwipedListener.invoke(swipedNote, NotepadSwipeDirection.LEFT)
        } else if (direction == ItemTouchHelper.LEFT) {
            onItemSwipedListener.invoke(swipedNote, NotepadSwipeDirection.RIGHT)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        val deleteIcon = recyclerView.context.getDrawable(R.drawable.ic_delete) ?: return
        val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2
        val iconTop = itemView.top + iconMargin
        val iconBottom = iconTop + deleteIcon.intrinsicHeight

        when {
            dX > 0 -> {
                archiveItemBackground.setBounds(
                    itemView.left,
                    itemView.top,
                    itemView.left + (dX.toInt() + BACKGROUND_CORNER_OFFSET),
                    itemView.bottom
                )

                val iconLeft = itemView.left + iconMargin
                val iconRight = itemView.left + iconMargin + deleteIcon.intrinsicWidth
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            }
            dX < 0 -> {
                deletedItemBackground.setBounds(
                    itemView.right + (dX.toInt() - BACKGROUND_CORNER_OFFSET),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
                val iconLeft = itemView.right - iconMargin - deleteIcon.intrinsicWidth
                val iconRight = itemView.right - iconMargin
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            }
            else -> {
                deletedItemBackground.setBounds(0, 0, 0, 0)
                archiveItemBackground.setBounds(0, 0, 0, 0)
            }
        }
        deletedItemBackground.draw(c)
        archiveItemBackground.draw(c)
        deleteIcon.draw(c)
    }
}
