package com.zbdd.harvestbook

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zbdd.harvestbook.model.INote
import com.zbdd.harvestbook.model.INoteRepository
import com.zbdd.harvestbook.model.Note
import com.zbdd.harvestbook.model.room.NoteEntity
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.TestInstance
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.time.LocalDateTime

/**
 * Unit test to confirm INoteRepository interface functions as expected
 * - with INote adhering objects able to be used in CRUD actions
 *
 *  @author Zac Durber
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseUnitTests {
    private lateinit var noteDao: INoteRepository

    @Before
    fun setup() {
        noteDao = FakeNoteDAO()
    }

    @AfterEach
    fun breakdown() {
        (noteDao as FakeNoteDAO).deleteAll()
    }

    @Test
    fun writeAndReadNote() {
        val note = NoteEntity(
            1,
            "title101",
            "this is a short note",
            LocalDateTime.now().minusDays(5).toString(),
            LocalDateTime.now().toString()
        )
        noteDao.create(note)
        val byId = noteDao.read(1)
        assert(note == byId)
    }

    @Test
    fun createMultiAndDeleteAll() {
        for(i in 1..5) {
            noteDao.create(NoteEntity(
                1,
                "title101",
                "this is a short note",
                LocalDateTime.now().minusDays(5).toString(),
                LocalDateTime.now().toString()
            ))
        }
        assertThat("NoteDAO ReadAll size incorrect", noteDao.readAll().size == 5)
    }

    @Test
    fun createMultiAndDeleteSpecific() {
        val listOfNotes = arrayListOf<INote>()
        for(i in 1..5) {
            val note = Note(
                i,
                "title101",
                "this is a short note",
                LocalDateTime.now().minusDays(5).toString(),
                LocalDateTime.now().toString()
            )
            listOfNotes.add(note)
            noteDao.create(note)
        }
        listOfNotes.find { it.id == 3 }?.let { noteDao.delete(it) }

        assertThat("NoteDAO delete 3 failed", noteDao.read(3) == null)
        assertThat("NoteDAO ReadAll size incorrect", noteDao.readAll().size == 4)
    }
}

class FakeNoteDAO: INoteRepository {
    private val noteList: MutableList<INote> = arrayListOf()

    fun deleteAll() {
        noteList.clear()
    }

    override fun create(entry: INote) {
        noteList.add(entry)
    }

    override fun readAll(): List<INote> {
        return noteList
    }

    override fun read(id: Int): INote? {
        return noteList.find { it.id == id }
    }

    override fun update(entry: INote) {
        val toUpdate = noteList.indices.find { noteList[it].id == entry.id }
        if (toUpdate != null) noteList[toUpdate] = entry
    }

    override fun delete(entry: INote) {
        val toUpdate = noteList.indices.find { noteList[it].id == entry.id }
        if (toUpdate != null) noteList.removeAt(toUpdate)
    }

}