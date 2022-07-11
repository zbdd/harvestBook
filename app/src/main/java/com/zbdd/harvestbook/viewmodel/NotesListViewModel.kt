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
class NotesListViewModel @Inject constructor() : ViewModel() {

    private val notes: INoteRepository = NoteRepository()
    var displayTopRow = true
    var noteList = arrayListOf<INote>()
    var displayDetail: INote? = null

    /**
     * On initialisation of this instance, fill the notesList with the latest data from the
     * database
     *  - the old demo notes function is for testing (manually insert some INotes)
     */
    init {
        //createDemoNotes()
        getAllNotes()
    }

    /**
     * Show a INote and stop displaying the top row
     *
     * @param note - the note to display
     */
    fun setupDetailedView(note: INote) {
        displayDetail = note
        displayTopRow = false
    }

    /**
     * Stop showing an INote and start showing the top row again
     */
    fun returnToList() {
        displayDetail = null
        displayTopRow = true
    }

    /**
     * A quick sort function that'll either use the Title or Updated properties as the comparison
     * item
     *
     * @param value - a string value to determine on what field will we sort by
     */
    fun sortListBy(value: String) {
        val comparator =
            when (value) {
                "Title" -> compareBy { note: INote -> note.title }
                else -> compareBy { note: INote -> convertStringToDateTime(note.updated.toString()) }.reversed()
            }

        val sorted = noteList.sortedWith(comparator)
        noteList.clear()
        noteList.addAll(sorted)
    }

    /**
     * Create a new note, add it to the DB and then render it to the screen
     */
    fun addNewNote() {
        val note = Note(
            noteList.size,
            "note",
            "",
            convertDateTimeToString(LocalDateTime.now()),
            convertDateTimeToString(LocalDateTime.now())
        )
        notes.create(note)

        setupDetailedView(note)
    }

    /**
     * Save any INote updates to the DB
     *
     * @param note - the note we want to save
     */
    fun saveNote(note: INote) {
        notes.update(note)
        getAllNotes()
    }

    /**
     * Clear the list and refresh it from the DB
     */
    fun getAllNotes() {
        noteList.clear()
        noteList.addAll(notes.readAll())
    }

    /**
     * Delete the INote passed in
     *
     * @param note to delete
     */
    fun removeNote(note: INote) {
        notes.delete(note)
        getAllNotes()
    }

    /**
     * Quick and nasty function to add some INotes to the database
     */
    fun createDemoNotes() {
        for (i in 0..7) {
            val note = Note(
                i,
                "title10${7-i}",
                "this is a short note",
                convertDateTimeToString(LocalDateTime.now().minusDays(i.toLong())),
                convertDateTimeToString(LocalDateTime.now().minusDays(i.toLong()))
            )
            notes.create(note)
        }
    }

    /**
     * Retrieve a single INote from the DB by the id field
     *
     * @param id - a integer representing a unique INote in the DB
     */
    fun getNote(id: Int) {
        displayDetail = notes.read(id)
        println("${displayDetail?.id}")
    }

    /**
     * A little function to change the updated and dateTime text when its close to the current day
     *
     * @param dateTime - a string of a valid dateTime that we want to convert
     */
    fun dateTimeEnhancer(dateTime: String): String {
        val now = LocalDateTime.now()
        val rawDT = LocalDateTime.parse(
            dateTime,
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm", Locale.ENGLISH)
        )

        if (rawDT.year == now.year) {
            if (rawDT.month == now.month) {
                if (rawDT.dayOfMonth == now.dayOfMonth) return "Today at ${dateTime.substringAfter(" ")}"
                if (rawDT.dayOfMonth == now.minusDays(1).dayOfMonth) return "Yesterday at ${
                    dateTime.substringAfter(
                        " "
                    )
                }"
            }
        }
        return dateTime.substringBefore(" ")
    }
}