package com.zbdd.harvestbook.model

/**
 * Our interface INote sets the required properties for any implementations of INote we will use
 * in the View and ViewModel layers later.
 *
 * @author Zac Durber
 */
interface INote {
    val id: Int
    var title: String?
    var content: String?
    var dateTime: String?
    var updated: String?
}