package com.first.test.model

import com.google.gson.annotations.SerializedName


data class TimeDetails(
                       @SerializedName("id")
                       var id:Long,
                       @SerializedName("title")
                       var title:String,
                       @SerializedName("abstract")
                       var abstract:String,
                       @SerializedName("url")
                       var url:String,
                       @SerializedName("media")
                       var media:List<MediaDetails>,
                       @SerializedName("published_date")
                       var published_date:String
                       )

data class MediaDetails(
    @SerializedName("type")
    var type:String,
    @SerializedName("media-metadata")
    var mediametadata:List<MediaMetaData>
)

data class MediaMetaData(
    @SerializedName("url")
    var url:String,
    @SerializedName("format")
    var format:String,
    @SerializedName("height")
    var height:Int,
    @SerializedName("width")
    var width:Int
)
