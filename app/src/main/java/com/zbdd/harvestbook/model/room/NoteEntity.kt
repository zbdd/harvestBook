package com.zbdd.harvestbook.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zbdd.harvestbook.model.INote

@Entity (tableName = "notes")
data class NoteEntity (
    @PrimaryKey
    override val id: Int,
    override var title: String?,
    override var content: String?,
    override var dateTime: String?,
    override var updated: String?
        ): INote