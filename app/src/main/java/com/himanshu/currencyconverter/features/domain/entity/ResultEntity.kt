package com.himanshu.currencyconverter.features.domain.entity


sealed class ResultEntity<out R> {
	data class Success<out T>(val data: T) : ResultEntity<T>()
	sealed class Failure : ResultEntity<Nothing>() {
		data class ErrorException(val exceptionError: Exception) : Failure()
		data class APIErrorFailure(
			val httpCode: Int,
			val responseMessage: String,
			val responseErrorBody: String?,
		) : Failure()
	}
}
