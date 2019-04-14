package com.first.test.data.local.db.dao

import android.arch.persistence.room.*
import com.first.test.data.local.db.entity.TImeInfoEntity
import android.arch.lifecycle.LiveData



@Dao
interface TimeDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertTimeDB(timeList: List<TImeInfoEntity>);

        @Query("SELECT * FROM TImeInfoEntity")
        fun getTimeInfoDB(): List<TImeInfoEntity>
}