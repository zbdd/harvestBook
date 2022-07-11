package com.zbdd.harvestbook.model.room

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery

/**
 * The INoteDAO is our interface for translating INoteRepository calls to a database to the
 * actual implementation of a RoomDatabase using ORM.
 * Extra functions added because of problems when converting the Kotlin language calls to Java
 * as we are using Hilt for Dependency Injection.
 *
 * @author Zac Durber
 */

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