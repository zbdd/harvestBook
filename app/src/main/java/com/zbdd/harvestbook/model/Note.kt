package com.zbdd.harvestbook.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Note(
    override val id: Int,
    override var title: String?,
    override var content: String?,
    override var dateTime: String?,
    override var updated: String?
) : INote

fun convertDateTimeToString(dateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm", Locale.ENGLISH)
    return formatter.format(dateTime)
}