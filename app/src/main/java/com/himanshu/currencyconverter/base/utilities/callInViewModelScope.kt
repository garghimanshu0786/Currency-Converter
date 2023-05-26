package com.himanshu.currencyconverter.base.utilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

fun <R> ViewModel.callInViewModelScope(block: suspend () -> R) {
	viewModelScope.launch {
		block()
	}
}