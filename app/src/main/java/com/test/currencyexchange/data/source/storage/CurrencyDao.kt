package com.test.currencyexchange.data.source.storage

import androidx.room.*
import com.test.currencyexchange.data.source.entity.DBCurrency
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM dbcurrency")
    fun get(): Single<List<DBCurrency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencies: List<DBCurrency>): Completable

    @Update
    fun update(dbCurrency: Array<out DBCurrency>): Completable
}