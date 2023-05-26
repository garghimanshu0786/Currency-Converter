package com.himanshu.currencyconverter.features.domain.usecases

import com.himanshu.currencyconverter.features.domain.DummyData.dummyConversionRates
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.Assertions

internal class CurrencyRateConversionUseCaseTest {

	@Test
	fun `CurrencyRateConversionUseCase performs correct rate conversions`() =
		runTest {
			//Setup
			val amountFilterUseCase = AmountFilterUseCase()
			val currencyRateConversionUseCase = CurrencyRateConversionUseCase(amountFilterUseCase)

			//Act
			val resultEntity =
				currencyRateConversionUseCase.invoke(dummyConversionRates, "10", "JPY")

			//Assert
			Assertions.assertEquals(mapOf("USD" to 5.0, "JPY" to 10.0), resultEntity)
		}
}