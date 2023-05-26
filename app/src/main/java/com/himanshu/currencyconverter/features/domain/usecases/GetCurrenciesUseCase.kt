package com.himanshu.currencyconverter.features.domain.usecases

import com.himanshu.currencyconverter.features.domain.ICurrencyRepository
import com.himanshu.currencyconverter.features.domain.entity.ResultEntity
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
	private val currencyRepository: ICurrencyRepository,
	private val shouldUpdateDataUseCase: ShouldUpdateDataUseCase
) {

	suspend operator fun invoke(): ResultEntity<Map<String, String>?> {
		val response: ResultEntity<Map<String, String>?>
		if (currencyRepository.getCurrenciesLocalStorageCount() == 0 || shouldUpdateDataUseCase.shouldRefreshData()) {
			response = currencyRepository.getCurrenciesFromRemote()
			if (response is ResultEntity.Success && response.data != null) {
				currencyRepository.insertCurrencies(response.data)
				shouldUpdateDataUseCase.updateLastDataUpdateTime()
			}
		} else {
			response = try {
				ResultEntity.Success(currencyRepository.getCurrenciesFromLocalSource())
			} catch (ex: Exception) {
				ResultEntity.Failure.ErrorException(ex)
			}
		}
		return response
	}
}
