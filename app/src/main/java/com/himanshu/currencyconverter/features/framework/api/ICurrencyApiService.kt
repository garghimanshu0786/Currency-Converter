package com.himanshu.currencyconverter.features.framework.api

import com.himanshu.currencyconverter.features.data.Constants.APP_CODE
import com.himanshu.currencyconverter.features.framework.network.models.ResponseRates
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ICurrencyApiService {

	@Headers("Authorization: Token $APP_CODE")
	@GET("currencies.json")
	suspend fun getCurrencies(): Response<Map<String, String>>

	@Headers("Authorization: Token $APP_CODE")
	@GET("latest.json")
	suspend fun getBaseCurrencyRate(): Response<ResponseRates>

}