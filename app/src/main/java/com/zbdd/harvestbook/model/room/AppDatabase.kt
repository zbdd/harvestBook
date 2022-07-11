package com.zbdd.harvestbook.model.room

import android.content.SharedPreferences
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.zbdd.harvestbook.MyApplication
import net.sqlcipher.database.SupportFactory


/**
 * A singleton instance of a Database wrapper that exposes the available DAO's to any calling
 * Repositories. The schemas (entities) are defined at the top and we call volatile on the DB variable
 * so that we are not caching requests/responses and potentially leading to bad-states.
 * In this instance, we are just creating a file for the DB to be instantiated in/ or if it exists,
 * reusing it.
 *
 * Using the sql cipher libraries with AndroidX to create/unlock an encrypted shareprefs file in the
 * app folder which houses our secret key for unlocking the database.
 *
 * SetInstance was added so that we could still do test doubling.
 *
 * @author Zac Durber
 */
@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        private const val DB_NAME = "harvest_book"
        private const val DB_DIR = "harvest_book"
        @Volatile
        private lateinit var DB: AppDatabase

        // Create or get a MasterKey from the Android Keystore
        private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        private val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        // Create/Reference a encrypted sharedPrefs which have our secret key for loading the DB
        private const val sharedPrefsFile: String = "prefs"
        private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            sharedPrefsFile,
            mainKeyAlias,
            MyApplication.appContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        /**
         * Get a single instance of the RoomDatabase
          */
        fun getInstance(): AppDatabase {
            if (!::DB.isInitialized) {
                val key = sharedPreferences.getString("key", mainKeyAlias).toString()
                val passphrase: ByteArray = key.toByteArray()
                val factory = SupportFactory(passphrase)

                // Check to see if there is an existing DB file
                val file = MyApplication.appContext.getDatabasePath(DB_DIR)
                if (file.exists())
                    setInstance(
                        Room.databaseBuilder(MyApplication.appContext, AppDatabase::class.java, DB_NAME)
                            .openHelperFactory(factory)
                            .createFromFile(file)
                            .build())
                else
                    setInstance(
                        Room.databaseBuilder(MyApplication.appContext, AppDatabase::class.java, DB_NAME)
                            .openHelperFactory(factory)
                            .build())
            }

            return DB
        }

        /**
         * Set the DB to an instance of AppDatabase
         *
         * @param appDatabase - instance of an AppDatabase
         */
        fun setInstance(appDatabase: AppDatabase) {
            DB = appDatabase
        }
    }

    /**
     * Return a reference to the DAO interface
     */
    abstract fun getNoteDAO(): INoteDAO
}