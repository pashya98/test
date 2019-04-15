package com.first.test.data.localdb

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.first.test.data.localdb.dao.TimeDao
import com.first.test.data.localdb.entity.TimeInfo

@Database(entities = [TimeInfo::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun timeDao():TimeDao

//    companion object {
//        private var mInstance: AppDatabase? = null
//        fun getInstance(context: Context): AppDatabase? {
//            if (mInstance == null) {
//                mInstance = Room.databaseBuilder(context,
//                    AppDatabase::class.java,
//                    "timer")
//                    .build()
//            }
//            return mInstance
//        }
//    }


    companion object {
        var INSTANCE: AppDatabase? = null

        fun getDB(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "myDB")
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }

}