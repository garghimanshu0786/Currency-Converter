package com.himanshu.currencyconverter.features.domain

import com.himanshu.currencyconverter.features.domain.entity.RatesEntity
import com.himanshu.currencyconverter.features.domain.entity.ResultEntity

interface ICurrencyRepository {

	suspend fun getCurrenciesFromRemote(): ResultEntity<Map<String, String>?>

	suspend fun getConversionRatesFromServer(): ResultEntity<RatesEntity?>

	suspend fun getCurrenciesFromLocalSource(): Map<String, String>

	suspend fun insertCurrencies(currencies: Map<String, String>)

	suspend fun getConversionRatesFromStorage(): Map<String, Double>

	suspend fun insertConversionRates(conversionRates: Map<String, Double>)

	suspend fun getConversionRatesLocalStorageCount(): Int

	suspend fun getCurrenciesLocalStorageCount(): Int
}