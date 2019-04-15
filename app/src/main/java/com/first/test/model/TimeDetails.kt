package com.first.test.model

import com.google.gson.annotations.SerializedName

data class TimeDetails(
                       @SerializedName("id")
                       var id:Long,
                       @SerializedName("adx_keywords")
                       var adx_keywords:String,
                       @SerializedName("column")
                       var column:String,
                       @SerializedName("section")
                       var section:String,
                       @SerializedName("byline")
                       var byline:String,
                       @SerializedName("type")
                       var type:String,
                       @SerializedName("title")
                       var title:String,
                       @SerializedName("abstract")
                       var abstract:String,
                       @SerializedName("published_date")
                       var published_date:String,
                       @SerializedName("source")
                       var source:String,
                       @SerializedName("asset_id")
                       var asset_id:Long,
                       @SerializedName("views")
                       var views:Int,
                       @SerializedName("url")
                       var url:String,
                       @SerializedName("media")
                       var media:List<MediaDetails>,
                       @SerializedName("uri")
                       var uri:String,
                       @SerializedName("des_facet")
                       var des_facet:Array<String>?
//                       @SerializedName("org_facet")
//                       var org_facet:Array<String>?
//                       @SerializedName("per_facet")
//                       var per_facet:Array<String>?,
//                       @SerializedName("geo_facet")
//                       var geo_facet:Array<String>?
){}


data class MediaDetails(
    @SerializedName("type")
    var type:String,
    @SerializedName("subtype")
    var subtype:String,
    @SerializedName("caption")
    var caption:String,
    @SerializedName("copyright")
    var copyright:String,
    @SerializedName("approved_for_syndication")
    var approved_for_syndication:Int,
    @SerializedName("media-metadata")
    var mediametadata:List<MediaMetaData>)

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
