package com.himanshu.currencyconverter.base.utilities

import com.himanshu.currencyconverter.features.presentation.models.APIExceptionEvent
import com.himanshu.currencyconverter.features.domain.entity.ResultEntity
import kotlinx.coroutines.flow.MutableSharedFlow

suspend fun handleException(
	response: ResultEntity.Failure,
	apiExceptionEvent: MutableSharedFlow<APIExceptionEvent>,
) {
	when (response) {
		is ResultEntity.Failure.APIErrorFailure -> {
			when (response.httpCode) {
				401 -> apiExceptionEvent.emit(APIExceptionEvent.Unauthorized)

				403 -> apiExceptionEvent.emit(APIExceptionEvent.Forbidden)

				else -> apiExceptionEvent.emit(
					APIExceptionEvent.Unknown(response.toString())
				)
			}
		}

		is ResultEntity.Failure.ErrorException -> {
			apiExceptionEvent.emit(APIExceptionEvent.Unknown(response.toString()))
		}
	}
}