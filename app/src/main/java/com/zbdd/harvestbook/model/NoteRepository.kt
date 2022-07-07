package com.zbdd.harvestbook.model

import com.zbdd.harvestbook.model.room.AppDatabase
import com.zbdd.harvestbook.model.room.NoteEntity

class NoteRepository: INoteRepository {
    private val DB = AppDatabase.getInstance()
    private val dao = DB.getNoteDAO()

    fun noteToEntity(entry: INote): NoteEntity {
        return NoteEntity(entry.id, entry.title, entry.content, entry.dateTime, entry.updated)
    }

    override fun create(entry: INote) {
        dao.create(noteToEntity(entry))
    }

    override fun readAll(): List<INote> {
        return dao.readAll()
    }

    override fun read(id: Int): INote? {
        return dao.read(id)
    }

    override fun update(entry: INote) {
        dao.update(noteToEntity(entry))
    }

    override fun delete(entry: INote) {
        dao.delete(noteToEntity(entry))
    }
}