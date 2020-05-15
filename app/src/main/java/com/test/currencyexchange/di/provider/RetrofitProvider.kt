package com.test.currencyexchange.di.provider

import com.google.gson.GsonBuilder
import com.test.currencyexchange.data.source.server.ServerApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider(
    private val okHttpClient: OkHttpClient,
    private val serverUrl: String
) {

    fun get(): ServerApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(serverUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ServerApi::class.java)
}