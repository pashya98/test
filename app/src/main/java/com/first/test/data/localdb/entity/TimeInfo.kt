package com.first.test.data.localdb.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "timeinfo")
data class TimeInfo(
    @PrimaryKey
    var id:Long,
    var type:String,
    var title:String,
    var sub_title:String,
    var published_date:String,
    var url:String){
    constructor() : this(0, "", "", "", "", "")
}