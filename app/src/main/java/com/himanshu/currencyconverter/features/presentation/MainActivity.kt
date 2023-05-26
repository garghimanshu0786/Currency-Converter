package com.himanshu.currencyconverter.features.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.himanshu.currencyconverter.features.presentation.theme.CurrencyConverterTheme
import com.himanshu.currencyconverter.features.presentation.ui.CurrencyConversionScreen
import com.himanshu.currencyconverter.features.presentation.ui.components.HandleAPIException
import com.himanshu.currencyconverter.features.presentation.ui.components.ShowProgress
import com.himanshu.currencyconverter.features.presentation.viewmodels.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val viewModel: CurrencyViewModel = hiltViewModel()
			val uiState by viewModel.uiState.collectAsStateWithLifecycle()
			val scaffoldState: ScaffoldState = rememberScaffoldState()
			CurrencyConverterTheme {
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
				) {
					CurrencyConversionScreen(scaffoldState, uiState, viewModel::onAmountChange, viewModel::onCurrencySelected)

					HandleAPIException(viewModel.apiExceptionEvent, scaffoldState)

					ShowProgress(uiState.isLoading)
				}
			}
		}
	}
}