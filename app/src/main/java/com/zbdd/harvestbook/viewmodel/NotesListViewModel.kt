package com.zbdd.harvestbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.room.withTransaction
import com.zbdd.harvestbook.model.room.AppDatabase
import com.zbdd.harvestbook.model.room.NoteEntity
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * NotesListViewModel handles state of properties shown in NotesListView
 *
 * @author Zac Durber
 */
class NotesListViewModel @Inject constructor(): ViewModel() {

    private var db: AppDatabase = AppDatabase.getInstance()

    fun getAllNotes(): List<NoteEntity> {
        val note = NoteEntity(
            1,
            "title101",
            "this is a short note",
            LocalDateTime.now().minusDays(5).toString(),
            LocalDateTime.now().toString()
        )

        val list = arrayListOf<NoteEntity>()
        val item = runBlocking { db.withTransaction {  db.getNoteRepo().create(note); db.getNoteRepo().read(1) } }
        if (item != null) list.add(item)
        return list
    }
}