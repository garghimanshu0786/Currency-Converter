package com.himanshu.currencyconverter.features.framework.models.databaseentities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyEntity(
	@PrimaryKey val symbol: String,
	val name: String
)
