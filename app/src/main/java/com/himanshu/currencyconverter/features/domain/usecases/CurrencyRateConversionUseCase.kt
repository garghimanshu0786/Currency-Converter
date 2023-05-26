package com.himanshu.currencyconverter.features.domain.usecases

import javax.inject.Inject

class CurrencyRateConversionUseCase @Inject constructor(private val amountFilterUseCase: AmountFilterUseCase) {

	operator fun invoke(
		baseConversionRates: Map<String, Double>, enteredAmount: String, selectedCurrency: String
	): Map<String, Double> {
		val multiplierFactor =
			amountFilterUseCase(enteredAmount).toDoubleOrNull() ?: return emptyMap()
		val selectedCurrencyValuePerUnitBaseCurrency =
			baseConversionRates[selectedCurrency] ?: return emptyMap()
		return baseConversionRates.mapValues {
			(it.value / selectedCurrencyValuePerUnitBaseCurrency) * multiplierFactor
		}
	}
}