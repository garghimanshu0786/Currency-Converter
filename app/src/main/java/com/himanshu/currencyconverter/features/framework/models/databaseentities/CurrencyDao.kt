package com.himanshu.currencyconverter.features.framework.models.databaseentities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CurrencyDao {
	@Query("SELECT * FROM currencyentity")
	suspend fun getAll(): List<CurrencyEntity>

	@Query("SELECT COUNT(*) FROM currencyentity")
	suspend fun getCount(): Int

	@Insert
	suspend fun insertAllCurrencies(currencies: List<CurrencyEntity>)

}