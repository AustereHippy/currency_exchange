package com.test.currencyexchange.data.source.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DBCurrency(
    @PrimaryKey val id: Int,
    val charCode: String,
    val scale: Int,
    val name: String,
    val visible: Boolean,
    val order: Int
)
