package com.zbdd.harvestbook.model.room

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.zbdd.harvestbook.model.INoteRepository

@Dao
interface INoteDAO: INoteRepository {
    override suspend fun create(entry: NoteEntity) {
        create(entry)
    }

    override suspend fun update(entry: NoteEntity) {
        update(entry)
    }

    override suspend fun delete(entry: NoteEntity) {
        delete(entry)
    }

    suspend fun readAll(): List<NoteEntity> {
        return readAllRaw(SimpleSQLiteQuery("SELECT * FROM notes"))
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(vararg noteEntities: NoteEntity)

    @RawQuery
    suspend fun readAllRaw(query: SimpleSQLiteQuery): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE id = :id")
    override suspend fun read(id: Int): NoteEntity?

    @Update
    suspend fun update(vararg noteEntities: NoteEntity)

    @Delete
    suspend fun delete(vararg noteEntities: NoteEntity)
}