package com.himanshu.currencyconverter.features.framework.models.databaseentities

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class ConversionRateEntity (
	@PrimaryKey val symbol: String,
	val rate: Double

)