package com.himanshu.currencyconverter.features.presentation.models

sealed class APIExceptionEvent {
	object Unauthorized : APIExceptionEvent()
	object Forbidden : APIExceptionEvent()
	class Unknown(val message: String) : APIExceptionEvent()
}