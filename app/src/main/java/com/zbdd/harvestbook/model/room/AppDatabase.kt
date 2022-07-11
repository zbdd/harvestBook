package com.zbdd.harvestbook.model.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zbdd.harvestbook.MyApplication
import java.io.File

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