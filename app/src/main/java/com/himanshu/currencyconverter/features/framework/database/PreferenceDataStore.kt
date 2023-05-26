package com.himanshu.currencyconverter.features.framework.database

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.himanshu.currencyconverter.features.data.source.ICurrencyLocalPreferenceStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val PREFERENCES_NAME = "app_preferences"

private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

class PreferenceDataStore @Inject constructor(
	@ApplicationContext private val context: Context
) : ICurrencyLocalPreferenceStorage {

	override suspend fun putLong(key: String, value: Long) {
		val preferencesKey = longPreferencesKey(key)
		context.dataStore.edit { preferences ->
			preferences[preferencesKey] = value
		}
	}

	override suspend fun getLong(key: String): Long {
		val preferencesKey = longPreferencesKey(key)
		val preferences = context.dataStore.data.first()
		return preferences[preferencesKey] ?: 0
	}
}