package com.test.currencyexchange.data.source.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.currencyexchange.data.source.entity.DBCurrency

@Database(
    entities = [DBCurrency::class],
    version = 1
)

abstract class Database : RoomDatabase() {
    abstract val currencyDao: CurrencyDao
}