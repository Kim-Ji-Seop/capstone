package com.uou.capstone.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://prod.capstoneuou.shop/api/app/"

fun getRetrofit(): Retrofit {
    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd")
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    return retrofit
}