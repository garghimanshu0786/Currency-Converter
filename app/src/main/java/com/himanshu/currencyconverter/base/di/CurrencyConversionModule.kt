package com.himanshu.currencyconverter.base.di

import com.himanshu.currencyconverter.features.data.repository.CurrencyRepository
import com.himanshu.currencyconverter.features.data.source.ICurrencyLocalDataSource
import com.himanshu.currencyconverter.features.data.source.ICurrencyLocalPreferenceStorage
import com.himanshu.currencyconverter.features.data.source.ICurrencyRemoteDataSource
import com.himanshu.currencyconverter.features.domain.ICurrencyRepository
import com.himanshu.currencyconverter.features.framework.api.ICurrencyApiService
import com.himanshu.currencyconverter.features.framework.database.CurrencyLocalDataSource
import com.himanshu.currencyconverter.features.framework.database.PreferenceDataStore
import com.himanshu.currencyconverter.features.framework.network.CurrencyRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrencyConversionModule {

	companion object {
		@Singleton
		@Provides
		fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

		@Singleton
		@Provides
		fun provideRetrofit(
			okHttpClient: OkHttpClient
		): Retrofit =
			Retrofit.Builder().baseUrl("https://openexchangerates.org/api/").client(okHttpClient)
				.addConverterFactory(GsonConverterFactory.create()).build()

		@Provides
		@Singleton
		fun provideCurrencyApiService(retrofit: Retrofit): ICurrencyApiService =
			retrofit.create(ICurrencyApiService::class.java)

	}

	@Binds
	abstract fun provideDataStoreRepository(preferenceDataStore: PreferenceDataStore): ICurrencyLocalPreferenceStorage

	@Binds
	abstract fun bindsCurrencyRepository(currencyRepository: CurrencyRepository): ICurrencyRepository

	@Binds
	abstract fun bindsCurrencyRemoteDataSource(currencyRemoteDataSource: CurrencyRemoteDataSource): ICurrencyRemoteDataSource

	@Binds
	abstract fun bindsCurrencyLocalDataSource(currencyLocalDataSource: CurrencyLocalDataSource): ICurrencyLocalDataSource

}