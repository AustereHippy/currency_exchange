package com.test.currencyexchange.application.system

import android.content.Context

class ResourceManager(private val context: Context) {

    fun getString(id: Int) = context.getString(id)
}