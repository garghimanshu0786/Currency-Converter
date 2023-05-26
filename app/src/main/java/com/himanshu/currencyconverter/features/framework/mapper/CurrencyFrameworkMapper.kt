package com.himanshu.currencyconverter.features.framework.mapper

import com.himanshu.currencyconverter.features.domain.entity.RatesEntity
import com.himanshu.currencyconverter.features.framework.network.models.ResponseRates
import com.himanshu.currencyconverter.features.framework.models.databaseentities.ConversionRateEntity
import com.himanshu.currencyconverter.features.framework.models.databaseentities.CurrencyEntity
import javax.inject.Inject

class CurrencyFrameworkMapper @Inject constructor() {
	fun toRatesEntity(response: ResponseRates?) = response?.let { RatesEntity(it.rates) }

	fun toCurrenciesDatabaseEntity(currencies: Map<String, String>) =
		currencies.map { CurrencyEntity(it.key, it.value) }

	fun toConversionRateDatabaseEntity(conversionRates: Map<String, Double>) = conversionRates.map {
		ConversionRateEntity(it.key, it.value)
	}
}