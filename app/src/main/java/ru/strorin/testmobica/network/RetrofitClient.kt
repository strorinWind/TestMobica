package ru.strorin.testmobica.network

import retrofit2.http.GET

interface RetrofitClient {

    @GET("home")
    suspend fun getList(): ResponseDTO
}