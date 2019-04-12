package com.first.test.network

import com.google.gson.Gson
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


class ApiClient {

  //  https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json?api-key=rECvXat5JIMkG59TNS8J4Ri2ZVHh8pYH
    companion object {
        val BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/"
        private var retrofit: Retrofit? = null
        fun getClient(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }


}