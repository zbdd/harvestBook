package com.zbdd.harvestbook.model

import com.zbdd.harvestbook.model.room.AppDatabase
import com.zbdd.harvestbook.model.room.NoteEntity
import kotlinx.coroutines.runBlocking

class NoteRepository: INoteRepository {
    private val DB = AppDatabase.getInstance()
    private val dao = DB.getNoteDAO()

    fun noteToEntity(entry: INote): NoteEntity {
        return NoteEntity(entry.id, entry.title, entry.content, entry.dateTime, entry.updated)
    }

    override fun create(entry: INote) {
        runBlocking {  dao.create(noteToEntity(entry)) }
    }

    override fun readAll(): List<INote> {
        return runBlocking { dao.readAll() }
    }

    override fun read(id: Int): INote? {
        return runBlocking { dao.read(id) }
    }

    override fun update(entry: INote) {
        runBlocking {  dao.update(noteToEntity(entry)) }
    }

    override fun delete(entry: INote) {
        runBlocking {  dao.delete(noteToEntity(entry)) }
    }
}