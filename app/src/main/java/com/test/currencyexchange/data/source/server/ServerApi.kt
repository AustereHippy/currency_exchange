package com.test.currencyexchange.data.source.server

import com.test.currencyexchange.data.source.entity.ApiCurrency
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApi {

    @GET("api/exrates/rates/")
    fun getCurrencyExchangeRates(
        @Query("ondate") date: String,
        @Query("periodicity") periodicity: Int = 0
    ): Single<List<ApiCurrency>>
}