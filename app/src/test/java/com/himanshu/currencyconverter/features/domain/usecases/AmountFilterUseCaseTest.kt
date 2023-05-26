package com.himanshu.currencyconverter.features.domain.usecases

import org.junit.Test
import org.junit.jupiter.api.Assertions

internal class AmountFilterUseCaseTest {

	@Test
	fun invoke() {
		//Setup
		val amountFilterUseCase = AmountFilterUseCase()

		//Act and Assert
		Assertions.assertEquals("", amountFilterUseCase(""))
		Assertions.assertEquals("1.0", amountFilterUseCase("1.0"))
		Assertions.assertEquals("1", amountFilterUseCase("-1.0"))
	}
}