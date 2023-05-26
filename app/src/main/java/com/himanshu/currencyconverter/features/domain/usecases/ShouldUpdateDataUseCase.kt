package com.himanshu.currencyconverter.features.domain.usecases

import com.himanshu.currencyconverter.features.data.source.ICurrencyLocalPreferenceStorage
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ShouldUpdateDataUseCase @Inject constructor(private val localPreferenceStorage: ICurrencyLocalPreferenceStorage) {

	suspend fun shouldRefreshData() =
		isOlderThanRefreshInterval(localPreferenceStorage.getLong(LAST_DATA_UPDATE_TIME))

	suspend fun updateLastDataUpdateTime() =
		localPreferenceStorage.putLong(LAST_DATA_UPDATE_TIME, System.currentTimeMillis())

	private fun isOlderThanRefreshInterval(previousTime: Long): Boolean = if (previousTime == 0L) {
		true
	} else {
		val hoursDiff = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis() - previousTime)
		hoursDiff > REFRESH_INTERVAL_IN_HOURS
	}

	companion object {
		const val LAST_DATA_UPDATE_TIME = "data_update_time"
		const val REFRESH_INTERVAL_IN_HOURS = 24
	}
}