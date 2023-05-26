package com.himanshu.currencyconverter.features.domain.usecases

import javax.inject.Inject

class AmountFilterUseCase @Inject constructor() {

	operator fun invoke(enteredAmount: String) =
		if (enteredAmount.isBlank()) "" else if (isInvalidNumber(enteredAmount)) "1" else enteredAmount

	private fun isInvalidNumber(enteredAmount: String) =
		enteredAmount.toDoubleOrNull() == null || enteredAmount.toDouble() <= 0.0

}