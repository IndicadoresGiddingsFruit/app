package com.example.indicadoresapp.ApiService

import com.example.indicadoresapp.ApiService.Constants.Companion.BaseUrl
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroInstance{
    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : IApiService by lazy{
        retrofit.create(IApiService::class.java)
    }
}