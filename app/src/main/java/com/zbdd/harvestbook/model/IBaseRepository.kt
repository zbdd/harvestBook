package com.zbdd.harvestbook.model

/**
 * Main interface defining our core functions required of our repositories
 *
 * @author Zac Durber
 */
interface IBaseRepository<T> {
    fun create(entry: T)
    fun readAll(): List<T>
    fun read(id: Int): T?
    fun update(entry: T)
    fun delete(entry: T)
}