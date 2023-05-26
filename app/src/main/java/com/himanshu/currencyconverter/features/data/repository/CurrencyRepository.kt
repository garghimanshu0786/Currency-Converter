package com.himanshu.currencyconverter.features.data.repository

import com.himanshu.currencyconverter.features.data.source.ICurrencyLocalDataSource
import com.himanshu.currencyconverter.features.data.source.ICurrencyRemoteDataSource
import com.himanshu.currencyconverter.features.domain.ICurrencyRepository
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
	private val remoteDataSource: ICurrencyRemoteDataSource,
	private val localDataSource: ICurrencyLocalDataSource,
) : ICurrencyRepository {

	override suspend fun getCurrenciesFromRemote() = remoteDataSource.getCurrencies()

	override suspend fun getConversionRatesFromServer() = remoteDataSource.getConversionRatesFromServer()

	override suspend fun getCurrenciesFromLocalSource() = localDataSource.getCurrencies()

	override suspend fun insertCurrencies(currencies: Map<String, String>) =
		localDataSource.insertCurrencies((currencies))

	override suspend fun getConversionRatesFromStorage() = localDataSource.getConversionRatesFromStorage()

	override suspend fun insertConversionRates(conversionRates: Map<String, Double>) =
		localDataSource.insertConversionRates(conversionRates)

	override suspend fun getConversionRatesLocalStorageCount() =
		localDataSource.getConversionRatesLocalStorageCount()

	override suspend fun getCurrenciesLocalStorageCount() =
		localDataSource.getCurrenciesLocalStorageCount()
}