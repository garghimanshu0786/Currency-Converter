package com.himanshu.currencyconverter.features.data.source

interface ICurrencyLocalPreferenceStorage {

	suspend fun putLong(key: String, value: Long)
	suspend fun getLong(key: String): Long
}
