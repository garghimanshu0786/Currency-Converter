package com.himanshu.currencyconverter.features.framework.models.databaseentities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ConversionRateDao {
	@Query("SELECT * FROM conversionrateentity")
	suspend fun getAll(): List<ConversionRateEntity>

	@Query("SELECT COUNT(*) FROM conversionrateentity")
	suspend fun getCount(): Int

	@Insert
	suspend fun insertAllRates(rates: List<ConversionRateEntity>)

}