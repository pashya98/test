package com.first.test.data.local.db

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.content.Context
import com.first.test.data.local.db.dao.TimeDao
import com.first.test.data.local.db.entity.TImeInfoEntity


@Database(entities = [TImeInfoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun timeDao(): TimeDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase::class.java, "time-database")
                        // allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE as AppDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}