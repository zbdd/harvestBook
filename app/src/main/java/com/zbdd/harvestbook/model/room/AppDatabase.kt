package com.zbdd.harvestbook.model.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zbdd.harvestbook.MyApplication

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        private const val DB_NAME = "harvest_book"
        private const val DB_DIR = "harvest.db"
        @Volatile
        private lateinit var DB: AppDatabase

        fun getInstance(): AppDatabase {
            if (!::DB.isInitialized)
                DB = Room
                .databaseBuilder(
                    MyApplication.appContext, AppDatabase::class.java,
                    DB_NAME)
                .createFromAsset(DB_DIR)
                .build()

            return DB
        }
    }

    abstract fun getNotes(): INoteDAO
}