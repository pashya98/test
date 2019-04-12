package com.first.test.network

import com.first.test.model.CommonResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("viewed/1.json")
    fun getTimeDetails(@Query("api-key") apiKey: String): Single<CommonResponse>
}