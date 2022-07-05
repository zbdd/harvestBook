package com.zbdd.harvestbook.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zbdd.harvestbook.model.INoteModel

@Entity (tableName = "notes")
data class NoteEntity (
    @PrimaryKey
    override val id: Int,
    override val title: String,
    override val content: String,
    override val dateTime: String,
    override val updated: String
        ): INoteModel