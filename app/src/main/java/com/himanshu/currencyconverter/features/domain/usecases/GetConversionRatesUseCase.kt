package com.himanshu.currencyconverter.features.domain.usecases

import com.himanshu.currencyconverter.features.domain.ICurrencyRepository
import com.himanshu.currencyconverter.features.domain.entity.RatesEntity
import com.himanshu.currencyconverter.features.domain.entity.ResultEntity
import javax.inject.Inject

class GetConversionRatesUseCase @Inject constructor(
	private val currencyRepository: ICurrencyRepository,
	private val shouldUpdateDataUseCase: ShouldUpdateDataUseCase,
) {
	suspend operator fun invoke(): ResultEntity<Map<String, Double>> =
		if (currencyRepository.getConversionRatesLocalStorageCount() == 0 || shouldUpdateDataUseCase.shouldRefreshData()) {
			getRatesFromApiAndSaveToStorage()
		} else {
			try {
				ResultEntity.Success(currencyRepository.getConversionRatesFromStorage())
			} catch (ex: java.lang.Exception) {
				ResultEntity.Failure.ErrorException(ex)
			}
		}

	private suspend fun getRatesFromApiAndSaveToStorage(): ResultEntity<Map<String, Double>> {
		val response = currencyRepository.getConversionRatesFromServer()
		var result: ResultEntity<Map<String, Double>>? = null
		if (response is ResultEntity.Success) {
			result = parseResultAndSaveToDbAndUpdateLastUpdatedTime(response)
		}
		return result ?: response as ResultEntity.Failure
	}

	private suspend fun parseResultAndSaveToDbAndUpdateLastUpdatedTime(
		response: ResultEntity.Success<RatesEntity?>,
	): ResultEntity<Map<String, Double>> = try {
		val conversionRates = response.data?.conversionRates?.mapValues {
			it.value.toDoubleOrNull() ?: 0.0
		} ?: emptyMap()
		currencyRepository.insertConversionRates(conversionRates)
		shouldUpdateDataUseCase.updateLastDataUpdateTime()
		ResultEntity.Success(conversionRates)
	} catch (ex: Exception) {
		ResultEntity.Failure.ErrorException(ex)
	}
}