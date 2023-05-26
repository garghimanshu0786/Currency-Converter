package com.himanshu.currencyconverter.features.domain.usecases

import com.himanshu.currencyconverter.features.domain.entity.ResultEntity
import com.himanshu.currencyconverter.features.domain.DummyData.dummyConversionRates
import com.himanshu.currencyconverter.features.domain.DummyData.dummyCurrencies
import com.himanshu.currencyconverter.features.domain.ICurrencyRepository
import com.himanshu.currencyconverter.features.domain.entity.RatesEntity
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.Assertions

internal class GetConversionRatesUseCaseTest {

	@Test
	fun `getConversionRatesUseCase returns correct data on API Success`() = runTest {
		//Setup
		val currencyRepository: ICurrencyRepository = mockk()
		coEvery { currencyRepository.getConversionRatesLocalStorageCount() } returns 0
		coEvery { currencyRepository.getConversionRatesFromServer() } returns ResultEntity.Success(
			RatesEntity(conversionRates = dummyCurrencies)
		)
		coEvery { currencyRepository.insertConversionRates(any()) } just runs
		val shouldUpdateDataUseCase: ShouldUpdateDataUseCase = mockk()
		coEvery { shouldUpdateDataUseCase.updateLastDataUpdateTime() } just runs
		val getConversionRatesUseCase =
			GetConversionRatesUseCase(currencyRepository, shouldUpdateDataUseCase)

		//Act
		val resultEntity = getConversionRatesUseCase.invoke()

		//Assert
		Assertions.assertEquals(
			ResultEntity.Success(mapOf("USD" to 0.0, "JPY" to 0.0)), resultEntity
		)
	}

	@Test
	fun `getConversionRatesUseCase returns error on API Failure`() = runTest {
		//Setup
		val currencyRepository: ICurrencyRepository = mockk()
		coEvery { currencyRepository.getConversionRatesLocalStorageCount() } returns 0
		val apiFailureResponse = ResultEntity.Failure.APIErrorFailure(
			httpCode = 0, responseMessage = "", null
		)
		coEvery { currencyRepository.getConversionRatesFromServer() } returns apiFailureResponse
		val getConversionRatesUseCase = GetConversionRatesUseCase(currencyRepository, mockk())

		//Act
		val resultEntity = getConversionRatesUseCase.invoke()

		//Assert
		Assertions.assertEquals(apiFailureResponse, resultEntity)
	}

	@Test
	fun `getConversionRatesUseCase returns data from storage when database contains conversion rates`() =
		runTest {
			//Setup
			val currencyRepository: ICurrencyRepository = mockk()
			coEvery { currencyRepository.getConversionRatesLocalStorageCount() } returns 1
			coEvery { currencyRepository.getConversionRatesFromStorage() } returns dummyConversionRates
			val shouldUpdateDataUseCase: ShouldUpdateDataUseCase = mockk()
			coEvery { shouldUpdateDataUseCase.shouldRefreshData() } returns false
			val getConversionRatesUseCase =
				GetConversionRatesUseCase(currencyRepository, shouldUpdateDataUseCase)

			//Act
			val resultEntity = getConversionRatesUseCase.invoke()

			//Assert
			Assertions.assertEquals(ResultEntity.Success(dummyConversionRates), resultEntity)
		}

	@Test
	fun `getConversionRatesUseCase fetch data from remote server when database contains conversion rates but the update data period has begun`() =
		runTest {
			//Setup
			val currencyRepository: ICurrencyRepository = mockk()
			coEvery { currencyRepository.getConversionRatesLocalStorageCount() } returns 1
			coEvery { currencyRepository.getConversionRatesFromServer() } returns ResultEntity.Success(
				RatesEntity(conversionRates = dummyCurrencies)
			)
			coEvery { currencyRepository.insertConversionRates(any()) } just runs
			val shouldUpdateDataUseCase: ShouldUpdateDataUseCase = mockk()
			coEvery { shouldUpdateDataUseCase.shouldRefreshData() } returns true
			coEvery { shouldUpdateDataUseCase.updateLastDataUpdateTime() } just runs
			val getConversionRatesUseCase =
				GetConversionRatesUseCase(currencyRepository, shouldUpdateDataUseCase)

			//Act
			val resultEntity = getConversionRatesUseCase.invoke()

			//Assert
			Assertions.assertEquals(
				ResultEntity.Success(mapOf("USD" to 0.0, "JPY" to 0.0)),
				resultEntity
			)
		}
}