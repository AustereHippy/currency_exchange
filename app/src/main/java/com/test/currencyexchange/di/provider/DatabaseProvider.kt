package com.test.currencyexchange.di.provider

import android.content.Context
import androidx.room.Room
import com.test.currencyexchange.data.source.storage.Database

class DatabaseProvider(private val context: Context) {

    fun get(): Database {
        return Room
            .databaseBuilder(context, Database::class.java, DB_NAME)
            .build()
    }

    companion object {
        const val DB_NAME = "currencyDB"
    }
}