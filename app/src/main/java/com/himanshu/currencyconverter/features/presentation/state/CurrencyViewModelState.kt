package com.himanshu.currencyconverter.features.presentation.state

data class CurrencyViewModelState(
	val isLoading: Boolean = false,
	val userEnteredAmount: String = "",
	val currencies: Map<String, String> = emptyMap(),
	val selectedCurrency: String = "",
	val baseConversionRates: Map<String, Double> = emptyMap(),
	val convertedRates: Map<String, Double> = emptyMap()
) {
	fun toUiState() = CurrencyUIState(
		isLoading,
		userEnteredAmount,
		currencies,
		selectedCurrency,
		baseConversionRates,
		convertedRates
	)
}

data class CurrencyUIState(
	val isLoading: Boolean,
	val userEnteredAmount: String,
	val currencies: Map<String, String>,
	val selectedCurrency: String,
	val baseConversionRates: Map<String, Double>,
	val convertedRates: Map<String, Double>
)