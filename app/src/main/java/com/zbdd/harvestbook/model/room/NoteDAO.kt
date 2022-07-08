package com.zbdd.harvestbook.model.room

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery

@Dao
interface INoteDAO {
    suspend fun create(entry: NoteEntity) {
        createRow(entry)
    }

    suspend fun update(entry: NoteEntity) {
        updateRow(entry)
    }

    suspend fun delete(entry: NoteEntity) {
        deleteRow(entry)
    }

    suspend fun readAll(): List<NoteEntity> {
        return readAllRaw(SimpleSQLiteQuery("SELECT * FROM notes"))
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createRow(vararg noteEntities: NoteEntity)

    @RawQuery
    suspend fun readAllRaw(query: SimpleSQLiteQuery): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun read(id: Int): NoteEntity?

    @Update
    suspend fun updateRow(vararg noteEntities: NoteEntity)

    @Delete
    suspend fun deleteRow(vararg noteEntities: NoteEntity)
}