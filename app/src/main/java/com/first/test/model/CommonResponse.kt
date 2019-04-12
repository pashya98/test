package com.first.test.model

import com.google.gson.annotations.SerializedName

data class CommonResponse(
                            @SerializedName("status")
                             var status:String,
                            @SerializedName("copyright")
                            var copyright:String,
                            @SerializedName("num_results")
                            var num_results:Int,
                            @SerializedName("results")
                            var results:List<TimeDetails>) {
}
