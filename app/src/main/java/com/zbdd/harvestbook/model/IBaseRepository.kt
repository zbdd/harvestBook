package com.zbdd.harvestbook.model

/**
 * Main interface defining our core functions required of our repositories
 *
 * @author Zac Durber
 */
interface IBaseRepository<T> {
    suspend fun create(entry: T)
    //suspend fun readAll(): List<T>
    suspend fun read(id: Int): T?
    suspend fun update(entry: T)
    suspend fun delete(entry: T)
}