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
    var noteList = arrayListOf<INote>()
    var displayDetail: INote? =  null

    fun setupDetailedView(note: INote) {
        displayDetail = note
        noteList.clear()
    }

    fun returnToList() {
        displayDetail = null
        noteList.addAll(notes.readAll())
    }

    init {
        createDemoNotes()
        noteList.addAll(notes.readAll())
    }

    fun createDemoNotes() {
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
    }

    fun getNote(id: Int) {
        displayDetail = notes.read(id)
        println("${displayDetail?.id}")
    }

    fun dateTimeEnhancer(dateTime:String): String {
        val now = LocalDateTime.now()
        val rawDT = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm", Locale.ENGLISH))

        if (rawDT.year == now.year) {
            if (rawDT.month == now.month) {
                if (rawDT.dayOfMonth == now.dayOfMonth) return "Today at ${dateTime.substringAfter(" ")}"
                if (rawDT.dayOfMonth == now.minusDays(1).dayOfMonth) return "Yesterday at ${dateTime.substringAfter(" ")}"
            }
        }
        return dateTime.substringBefore(" ")
    }
}