package com.himanshu.currencyconverter.features.domain.usecases

import com.himanshu.currencyconverter.features.domain.entity.ResultEntity
import com.himanshu.currencyconverter.features.domain.DummyData.dummyCurrencies
import com.himanshu.currencyconverter.features.domain.ICurrencyRepository
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class GetCurrenciesUseCaseTest {

	private val dummyAPIResponse =
		ResultEntity.Success(dummyCurrencies)

	@Test
	fun `GetCurrenciesUseCase returns correct data on API Success`() = runTest {
		//Setup
		val currencyRepository: ICurrencyRepository = mockk()
		coEvery { currencyRepository.getCurrenciesFromRemote() } returns dummyAPIResponse
		coEvery { currencyRepository.insertCurrencies(any()) } just runs
		coEvery { currencyRepository.getCurrenciesLocalStorageCount() } returns 0
		val shouldUpdateDataUseCase: ShouldUpdateDataUseCase = mockk()
		coEvery { shouldUpdateDataUseCase.updateLastDataUpdateTime() } just runs
		val getConversionRatesUseCase =
			GetCurrenciesUseCase(currencyRepository, shouldUpdateDataUseCase)

		//Act
		val resultEntity = getConversionRatesUseCase.invoke()

		//Assert
		assertEquals(dummyAPIResponse, resultEntity)
	}

	@Test
	fun `GetCurrenciesUseCase returns error on API Failure`() = runTest {
		//Setup
		val apiFailureResponse = ResultEntity.Failure.APIErrorFailure(
			httpCode = 0, responseMessage = "", null
		)
		val currencyRepository: ICurrencyRepository = mockk()
		coEvery { currencyRepository.getCurrenciesFromRemote() } returns apiFailureResponse
		coEvery { currencyRepository.getCurrenciesLocalStorageCount() } returns 0
		val getConversionRatesUseCase = GetCurrenciesUseCase(currencyRepository, mockk())

		//Act
		val resultEntity = getConversionRatesUseCase.invoke()

		//Assert
		assertEquals(apiFailureResponse, resultEntity)
	}

	@Test
	fun `GetCurrenciesUseCase returns data from storage when database contains currencies`() =
		runTest {
			//Setup
			val currencyRepository: ICurrencyRepository = mockk()
			coEvery { currencyRepository.getCurrenciesLocalStorageCount() } returns 1
			coEvery { currencyRepository.getCurrenciesFromLocalSource() } returns dummyAPIResponse.data
			val shouldUpdateDataUseCase: ShouldUpdateDataUseCase = mockk()
			coEvery { shouldUpdateDataUseCase.shouldRefreshData() } returns false
			val getConversionRatesUseCase =
				GetCurrenciesUseCase(currencyRepository, shouldUpdateDataUseCase)

			//Act
			val resultEntity = getConversionRatesUseCase.invoke()

			//Assert
			assertEquals(dummyAPIResponse, resultEntity)
		}

	@Test
	fun `GetCurrenciesUseCase fetch data from remote server when database contains conversion rates but the update data period has begun`() =
		runTest {
			//Setup
			val currencyRepository: ICurrencyRepository = mockk()
			coEvery { currencyRepository.getCurrenciesLocalStorageCount() } returns 1
			coEvery { currencyRepository.getCurrenciesFromRemote() } returns dummyAPIResponse
			coEvery { currencyRepository.insertCurrencies(any()) } just runs
			val shouldUpdateDataUseCase: ShouldUpdateDataUseCase = mockk()
			coEvery { shouldUpdateDataUseCase.shouldRefreshData() } returns true
			coEvery { shouldUpdateDataUseCase.updateLastDataUpdateTime() } just runs
			val getConversionRatesUseCase =
				GetCurrenciesUseCase(currencyRepository, shouldUpdateDataUseCase)

			//Act
			val resultEntity = getConversionRatesUseCase.invoke()

			//Assert
			assertEquals(dummyAPIResponse, resultEntity)
		}
}