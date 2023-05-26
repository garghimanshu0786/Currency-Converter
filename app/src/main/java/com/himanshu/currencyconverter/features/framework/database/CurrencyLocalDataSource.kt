package com.himanshu.currencyconverter.features.framework.database

import android.content.Context
import androidx.room.Room
import com.himanshu.currencyconverter.features.data.source.ICurrencyLocalDataSource
import com.himanshu.currencyconverter.features.framework.mapper.CurrencyFrameworkMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CurrencyLocalDataSource @Inject constructor(
	@ApplicationContext context: Context,
	private val mapper: CurrencyFrameworkMapper
) : ICurrencyLocalDataSource {
	private val db =
		Room.databaseBuilder(context, AppDatabase::class.java, "currency-database").build()
	private val currencyDao = db.currencyDao()
	private val conversionRateDao = db.conversionRateDao()

	override suspend fun getCurrencies(): Map<String, String> =
		currencyDao.getAll().associate { it.symbol to it.name }

	override suspend fun getCurrenciesLocalStorageCount(): Int = currencyDao.getCount()

	override suspend fun insertCurrencies(currencies: Map<String, String>) =
		currencyDao.insertAllCurrencies(mapper.toCurrenciesDatabaseEntity(currencies))

	override suspend fun getConversionRatesFromStorage(): Map<String, Double> =
		conversionRateDao.getAll().associate { it.symbol to it.rate }

	override suspend fun getConversionRatesLocalStorageCount(): Int = conversionRateDao.getCount()

	override suspend fun insertConversionRates(conversionRates: Map<String, Double>) =
		conversionRateDao.insertAllRates(mapper.toConversionRateDatabaseEntity(conversionRates))
}