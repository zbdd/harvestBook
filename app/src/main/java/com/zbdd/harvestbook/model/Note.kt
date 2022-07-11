package com.zbdd.harvestbook.model

/**
 * Implementation of the INote interface to be used in the View and View Model layers ONLY
 * Supplied with helper functions for dateTime conversion.
 *
 * @author Zac Durber
 */
data class Note(
    override val id: Int,
    override var title: String?,
    override var content: String?,
    override var dateTime: String?,
    override var updated: String?
) : INote