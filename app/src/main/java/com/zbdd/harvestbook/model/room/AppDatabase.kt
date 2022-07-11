package com.zbdd.harvestbook.model.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zbdd.harvestbook.MyApplication
import java.io.File

/**
 * A singleton instance of a Database wrapper that exposes the available DAO's to any calling
 * Repositories. The schemas (entities) are defined at the top and we call volatile on the DB variable
 * so that we are not caching requests/responses and potentially leading to bad-states.
 * In this instance, we are just creating a file for the DB to be instantiated in/ or if it exists,
 * reusing it.
 *
 * SetInstance was added so that we could still do test doubling however, Hilt + Room + Unit Tests
 * are a mess.
 *
 * @author Zac Durber
 */
@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        private const val DB_NAME = "harvest_book"
        private const val DB_ASSET_FILE = "harvest_book"
        @Volatile
        private lateinit var DB: AppDatabase

        fun getInstance(): AppDatabase {
            val file = File(DB_ASSET_FILE)

            if (!::DB.isInitialized) setInstance(Room
                .databaseBuilder(
                    MyApplication.appContext, AppDatabase::class.java,
                    DB_NAME)
                .createFromFile(file)
                .build())

            return DB
        }

        fun setInstance(appDatabase: AppDatabase) {
            DB = appDatabase
        }
    }

    abstract fun getNoteDAO(): INoteDAO
}