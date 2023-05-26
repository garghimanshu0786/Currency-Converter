package com.himanshu.currencyconverter.features.data.source

import com.himanshu.currencyconverter.features.domain.entity.ResultEntity
import com.himanshu.currencyconverter.features.domain.entity.RatesEntity

interface ICurrencyRemoteDataSource {

	suspend fun getCurrencies(): ResultEntity<Map<String, String>?>

	suspend fun getConversionRatesFromServer(): ResultEntity<RatesEntity?>
}