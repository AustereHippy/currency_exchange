package com.test.currencyexchange.data.source.entity

import com.google.gson.annotations.SerializedName

data class ApiCurrency(
    @SerializedName("Cur_ID") val id: Int,
    @SerializedName("Date") val date: String,
    @SerializedName("Cur_Abbreviation") val charCode: String,
    @SerializedName("Cur_Scale") val scale: Int,
    @SerializedName("Cur_Name") val name: String,
    @SerializedName("Cur_OfficialRate") val rate: Float
)