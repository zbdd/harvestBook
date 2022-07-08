package com.zbdd.harvestbook.viewmodel

import androidx.lifecycle.ViewModel
import com.zbdd.harvestbook.model.INote
import com.zbdd.harvestbook.model.INoteRepository
import com.zbdd.harvestbook.model.Note
import com.zbdd.harvestbook.model.NoteRepository
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * NotesListViewModel handles state of properties shown in NotesListView
 *
 * @author Zac Durber
 */
class NotesListViewModel @Inject constructor(): ViewModel() {

   private val notes: INoteRepository = NoteRepository()

    fun getAllNotes(): List<INote> {
        for (i in 1..7) {
            val note = Note(
                i,
                "title101",
                "this is a short note",
                LocalDateTime.now().minusDays(i.toLong()).toString(),
                LocalDateTime.now().toString()
            )
            notes.create(note)
        }

        return notes.readAll()
    }
}