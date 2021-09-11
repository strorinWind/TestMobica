package ru.strorin.testmobica.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RetrofitObject {

    private val client = OkHttpClient.Builder()
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @ExperimentalSerializationApi
    private var retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://financialmodelingprep.com/api/v3/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @ExperimentalSerializationApi
    val retrofitClient: RetrofitClient = retrofit.create(RetrofitClient::class.java)
}