package com.zbdd.harvestbook.viewmodel

import androidx.lifecycle.ViewModel
import com.zbdd.harvestbook.model.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

/**
 * NotesListViewModel handles state of properties shown in NotesListView
 *
 * @author Zac Durber
 */
class NotesListViewModel @Inject constructor(): ViewModel() {

   private val notes: INoteRepository = NoteRepository()

    fun getAllNotes(): List<INote> {
        for (i in 0..7) {
            val note = Note(
                i,
                "title10${i}",
                "this is a short note",
                convertDateTimeToString(LocalDateTime.now().minusDays(i.toLong())),
                convertDateTimeToString(LocalDateTime.now().minusDays(i.toLong()))
            )
            notes.create(note)
        }

        return notes.readAll()
    }

    fun dateTimeEnhancer(dateTime:String): String {
        val now = LocalDateTime.now()
        val rawDT = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm", Locale.ENGLISH))

        if (rawDT.year == now.year) {
            if (rawDT.month == now.month) {
                if (rawDT.dayOfMonth == now.dayOfMonth) return "Today"
                if (rawDT.dayOfMonth == now.minusDays(1).dayOfMonth) return "Yesterday"
            }
        }
        return dateTime.substringBefore(" ")
    }
}