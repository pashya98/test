package com.first.test.data.local.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class TImeInfoEntity(@PrimaryKey
                          var id:Long,
                          var title:String,
                          var abstract:String,
                          var published_date:String,
                          var url:String)