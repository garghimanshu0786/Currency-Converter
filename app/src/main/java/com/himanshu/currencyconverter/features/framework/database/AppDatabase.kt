package com.himanshu.currencyconverter.features.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.himanshu.currencyconverter.features.framework.models.databaseentities.ConversionRateDao
import com.himanshu.currencyconverter.features.framework.models.databaseentities.ConversionRateEntity
import com.himanshu.currencyconverter.features.framework.models.databaseentities.CurrencyDao
import com.himanshu.currencyconverter.features.framework.models.databaseentities.CurrencyEntity

@Database(entities = [CurrencyEntity::class, ConversionRateEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
	abstract fun currencyDao(): CurrencyDao
	abstract fun conversionRateDao(): ConversionRateDao
}