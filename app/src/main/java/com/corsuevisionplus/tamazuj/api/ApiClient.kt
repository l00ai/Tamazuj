package com.corsuevisionplus.tamazuj.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder


class ApiClient {
    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://tamazuj.herokuapp.com/"

    var gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit
        }
}