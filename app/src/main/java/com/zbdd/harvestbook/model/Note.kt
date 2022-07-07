package com.zbdd.harvestbook.model

class Note(
    override val id: Int,
    override val title: String?,
    override val content: String?,
    override val dateTime: String?,
    override val updated: String?
) : INote {
}