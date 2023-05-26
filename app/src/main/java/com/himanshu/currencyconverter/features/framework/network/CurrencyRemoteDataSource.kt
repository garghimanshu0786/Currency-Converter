package com.himanshu.currencyconverter.features.framework.network

import com.himanshu.currencyconverter.base.utilities.callAPI
import com.himanshu.currencyconverter.features.framework.api.ICurrencyApiService
import com.himanshu.currencyconverter.features.data.source.ICurrencyRemoteDataSource
import com.himanshu.currencyconverter.features.framework.mapper.CurrencyFrameworkMapper
import javax.inject.Inject

class CurrencyRemoteDataSource @Inject constructor(
	private val currencyApiService: ICurrencyApiService, private val mapper: CurrencyFrameworkMapper
) : ICurrencyRemoteDataSource {
	override suspend fun getCurrencies() = callAPI(currencyApiService::getCurrencies) { it }

	override suspend fun getConversionRatesFromServer() =
		callAPI(currencyApiService::getBaseCurrencyRate, mapper::toRatesEntity)
}