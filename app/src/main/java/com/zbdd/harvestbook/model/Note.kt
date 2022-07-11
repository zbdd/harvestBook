package com.zbdd.harvestbook.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Implementation of the INote interface to be used in the View and View Model layers ONLY
 * Supplied with helper functions for dateTime conversion.
 *
 * @author Zac Durber
 */
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

fun convertStringToDateTime(dateTime: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm", Locale.ENGLISH)
    return LocalDateTime.parse(dateTime, formatter)
}