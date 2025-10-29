package com.example.roomdatabase_140

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataKewarganegaraan::class], version = 1, exportSchema = false)
abstract class KewarganegaraanDatabase : RoomDatabase() {

    abstract fun dataKewarganegaraanDao(): DataKewarganegaraanDao

    companion object {
        @Volatile
        private var INSTANCE: KewarganegaraanDatabase? = null

        fun getDatabase(context: Context): KewarganegaraanDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KewarganegaraanDatabase::class.java,
                    "kewarganegaraan_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
