package com.first.test.data.localdb.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.first.test.data.localdb.entity.TimeInfo
import io.reactivex.Maybe
import io.reactivex.Flowable



@Dao
interface TimeDao {
    @Query("SELECT * FROM timeinfo")
    fun getTimeList(): Maybe<List<TimeInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(options: List<TimeInfo>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(options: TimeInfo): Long

    @Query("SELECT * FROM TimeInfo WHERE id = :id")
    fun getTimeDetails(id: Long): Flowable<TimeInfo>
}