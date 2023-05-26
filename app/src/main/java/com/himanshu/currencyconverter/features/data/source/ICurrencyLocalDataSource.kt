package com.himanshu.currencyconverter.features.data.source

interface ICurrencyLocalDataSource {

	suspend fun getCurrencies(): Map<String, String>

	suspend fun insertCurrencies(currencies: Map<String, String>)

	suspend fun getConversionRatesFromStorage(): Map<String, Double>

	suspend fun insertConversionRates(conversionRates: Map<String, Double>)

	suspend fun getCurrenciesLocalStorageCount(): Int

	suspend fun getConversionRatesLocalStorageCount(): Int
}
