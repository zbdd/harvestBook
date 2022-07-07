package com.zbdd.harvestbook.model.room

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery

@Dao
interface INoteDAO {
     fun create(entry: NoteEntity) {
        createRow(entry)
    }

     fun update(entry: NoteEntity) {
        updateRow(entry)
    }

     fun delete(entry: NoteEntity) {
        deleteRow(entry)
    }

     fun readAll(): List<NoteEntity> {
        return readAllRaw(SimpleSQLiteQuery("SELECT * FROM notes"))
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createRow(vararg noteEntities: NoteEntity)

    @RawQuery
    fun readAllRaw(query: SimpleSQLiteQuery): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE id = :id")
     fun read(id: Int): NoteEntity?

    @Update
    fun updateRow(vararg noteEntities: NoteEntity)

    @Delete
    fun deleteRow(vararg noteEntities: NoteEntity)
}