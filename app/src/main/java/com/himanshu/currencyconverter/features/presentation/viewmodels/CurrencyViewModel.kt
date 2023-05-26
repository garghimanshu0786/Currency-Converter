package com.himanshu.currencyconverter.features.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.currencyconverter.base.utilities.callInViewModelScope
import com.himanshu.currencyconverter.base.utilities.handleException
import com.himanshu.currencyconverter.features.domain.entity.ResultEntity
import com.himanshu.currencyconverter.features.domain.usecases.AmountFilterUseCase
import com.himanshu.currencyconverter.features.domain.usecases.CurrencyRateConversionUseCase
import com.himanshu.currencyconverter.features.domain.usecases.GetConversionRatesUseCase
import com.himanshu.currencyconverter.features.domain.usecases.GetCurrenciesUseCase
import com.himanshu.currencyconverter.features.presentation.models.APIExceptionEvent
import com.himanshu.currencyconverter.features.presentation.state.CurrencyViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
	private val currencyConversionUseCase: CurrencyRateConversionUseCase,
	private val getConversionRatesUseCase: GetConversionRatesUseCase,
	private val getCurrenciesUseCase: GetCurrenciesUseCase,
	private val amountFilterUseCase: AmountFilterUseCase,
	private val savedStateHandle: SavedStateHandle
) : ViewModel() {
	private val viewModelState = MutableStateFlow(CurrencyViewModelState())
	val uiState = viewModelState.map { it.toUiState() }
		.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), viewModelState.value.toUiState())

	private val _apiExceptionEvent = MutableSharedFlow<APIExceptionEvent>()
	val apiExceptionEvent: SharedFlow<APIExceptionEvent> get() = _apiExceptionEvent

	init {
		restoreState()
		getAllCurrencies()
		getConversionRatesFromServer()
	}

	private fun restoreState() {
		val userEnteredAmount = savedStateHandle.get<String>(
			USER_ENTERED_AMOUNT_SAVED_STATE_HANDLE_KEY
		).orEmpty()
		val selectedCurrency = savedStateHandle.get<String>(
			SELECTED_CURRENCY_SAVED_STATE_HANDLE_KEY
		).orEmpty()

		viewModelState.update {
			it.copy(userEnteredAmount = userEnteredAmount, selectedCurrency = selectedCurrency)
		}
	}

	private fun getConversionRatesFromServer() = callInViewModelScope {
		updateLoader(true)

		when (val response = getConversionRatesUseCase()) {
			is ResultEntity.Success -> viewModelState.update {
				it.copy(
					baseConversionRates = response.data, convertedRates = currencyConversionUseCase(
						response.data, it.userEnteredAmount, it.selectedCurrency
					)
				)
			}

			is ResultEntity.Failure -> handleException(response, _apiExceptionEvent)
		}

		updateLoader(false)
	}

	private fun getAllCurrencies() = callInViewModelScope {
		updateLoader(true)

		when (val response = getCurrenciesUseCase()) {
			is ResultEntity.Success -> viewModelState.update {
				it.copy(currencies = response.data.orEmpty())
			}

			is ResultEntity.Failure -> handleException(response, _apiExceptionEvent)
		}

		updateLoader(false)
	}

	fun onAmountChange(enteredAmount: String) {
		savedStateHandle[USER_ENTERED_AMOUNT_SAVED_STATE_HANDLE_KEY] = enteredAmount
		viewModelState.update {
			it.copy(
				convertedRates = currencyConversionUseCase(
					it.baseConversionRates, enteredAmount, it.selectedCurrency
				), userEnteredAmount = amountFilterUseCase(enteredAmount)
			)
		}
	}

	fun onCurrencySelected(currency: String) {
		savedStateHandle[SELECTED_CURRENCY_SAVED_STATE_HANDLE_KEY] = currency
		viewModelState.update {
			it.copy(
				selectedCurrency = currency, convertedRates = currencyConversionUseCase(
					it.baseConversionRates, it.userEnteredAmount, currency
				)
			)
		}
	}

	private fun updateLoader(updateLoader: Boolean) = viewModelState.update {
		it.copy(isLoading = updateLoader)
	}

	companion object {
		const val USER_ENTERED_AMOUNT_SAVED_STATE_HANDLE_KEY = "user_entered_amount"
		const val SELECTED_CURRENCY_SAVED_STATE_HANDLE_KEY = "selected_currency"
	}

}