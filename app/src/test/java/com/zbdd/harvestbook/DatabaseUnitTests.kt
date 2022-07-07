package com.zbdd.harvestbook

import androidx.room.Room
import androidx.room.withTransaction
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zbdd.harvestbook.model.INoteRepository
import com.zbdd.harvestbook.model.room.AppDatabase
import com.zbdd.harvestbook.model.room.INoteDAO
import com.zbdd.harvestbook.model.room.NoteEntity
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.res.android.ResTable_config
import java.time.LocalDateTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
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
}

class FakeNoteDAO: INoteDAO {
    private val noteList: MutableList<NoteEntity> = arrayListOf()

    override fun createRow(vararg noteEntities: NoteEntity) {
        noteList += noteEntities
    }

    override fun readAllRaw(query: SimpleSQLiteQuery): List<NoteEntity> {
        return noteList
    }

    override fun read(id: Int): NoteEntity? {
        return noteList.find { it.id == id }
    }

    override fun updateRow(vararg noteEntities: NoteEntity) {
        noteList.indices.forEach { notePos -> noteEntities.forEach { if (noteList[notePos].id == it.id) noteList[notePos] = it } }
    }

    override fun deleteRow(vararg noteEntities: NoteEntity) {
        val toDelete = arrayListOf<Int>()
        noteList.indices.forEach { notePos -> noteEntities.forEach { if (noteList[notePos].id == it.id) toDelete.add(notePos) } }
        toDelete.forEach { noteList.removeAt(it) }
    }

}