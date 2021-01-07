package com.dvinc.core.database.dao.archive

import androidx.room.Dao
import androidx.room.Query
import com.dvinc.core.database.entity.note.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArchiveDao {

    @Query("UPDATE Notes SET is_archived = 1 WHERE id =:noteId")
    suspend fun markNoteAsArchived(noteId: Long)

    @Query("SELECT * FROM Notes WHERE is_archived = 1 ORDER BY id DESC")
    fun getArchive(): Flow<List<NoteEntity>>
}
