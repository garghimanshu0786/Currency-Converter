package com.himanshu.currencyconverter.features.domain.usecases

import com.himanshu.currencyconverter.features.data.source.ICurrencyLocalPreferenceStorage
import com.himanshu.currencyconverter.features.domain.usecases.ShouldUpdateDataUseCase.Companion.REFRESH_INTERVAL_IN_HOURS
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

internal class ShouldUpdateDataUseCaseTest {

	@Test
	fun `ShouldUpdateDataUseCase should return false for shouldRefreshData for current time`() =
		runTest {
			//Setup
			val localPreferenceStorage: ICurrencyLocalPreferenceStorage = mockk()
			coEvery { localPreferenceStorage.getLong(ShouldUpdateDataUseCase.LAST_DATA_UPDATE_TIME) } returns System.currentTimeMillis()
			val shouldUpdateDataUseCase = ShouldUpdateDataUseCase(localPreferenceStorage)

			//Act
			val shouldRefreshData = shouldUpdateDataUseCase.shouldRefreshData()

			//Assert
			Assert.assertEquals(false, shouldRefreshData)
		}

	@Test
	fun `ShouldUpdateDataUseCase should return true for shouldRefreshData for current time added with update interval`() =
		runTest {
			//Setup
			val localPreferenceStorage: ICurrencyLocalPreferenceStorage = mockk()
			coEvery { localPreferenceStorage.getLong(ShouldUpdateDataUseCase.LAST_DATA_UPDATE_TIME) } returns System.currentTimeMillis()
				.plus(REFRESH_INTERVAL_IN_HOURS)
			val shouldUpdateDataUseCase = ShouldUpdateDataUseCase(localPreferenceStorage)

			//Act
			val shouldRefreshData = shouldUpdateDataUseCase.shouldRefreshData()

			//Assert
			Assert.assertEquals(false, shouldRefreshData)
		}

	@Test
	fun `ShouldUpdateDataUseCase should save lastupdatedtime to updateLastDataUpdateTime on data updation`() =
		runTest {
			//Setup
			val localPreferenceStorage: ICurrencyLocalPreferenceStorage = mockk()
			coEvery {
				localPreferenceStorage.putLong(ShouldUpdateDataUseCase.LAST_DATA_UPDATE_TIME, any())
			} just runs
			val shouldUpdateDataUseCase = ShouldUpdateDataUseCase(localPreferenceStorage)

			//Act
			shouldUpdateDataUseCase.updateLastDataUpdateTime()

			//Assert
			coVerify(exactly = 1) {
				localPreferenceStorage.putLong(ShouldUpdateDataUseCase.LAST_DATA_UPDATE_TIME, any())
			}
		}
}