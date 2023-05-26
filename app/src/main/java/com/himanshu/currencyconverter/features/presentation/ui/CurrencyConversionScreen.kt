package com.himanshu.currencyconverter.features.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.himanshu.currencyconverter.R
import com.himanshu.currencyconverter.features.presentation.state.CurrencyUIState
import com.himanshu.currencyconverter.features.presentation.ui.components.ConvertedRatesGrid
import com.himanshu.currencyconverter.features.presentation.ui.components.CurrencyDropdown

@Composable
fun CurrencyConversionScreen(
	scaffoldState: ScaffoldState,
	uiState: CurrencyUIState,
	onAmountChange: (String) -> Unit,
	onCurrencySelected: (String) -> Unit
) {
	Scaffold(scaffoldState = scaffoldState, topBar = {
		Column {
			TextField(
				label = {
					Text(text = stringResource(id = R.string.enter_amount))
				},
				value = uiState.userEnteredAmount,
				onValueChange = onAmountChange,
				keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
				modifier = Modifier.fillMaxWidth()
			)

			Row(verticalAlignment = Alignment.CenterVertically) {
				Spacer(Modifier.weight(1f))

				Text(
					text = stringResource(id = R.string.select_your_currency),
					Modifier.padding(top = 20.dp, end = 20.dp),
					color = MaterialTheme.colors.primary
				)

				CurrencyDropdown(
					uiState.currencies.keys.toList(), uiState.selectedCurrency, onCurrencySelected
				)
			}

			Spacer(Modifier.height(20.dp))
		}
	}) {
		Column(
			Modifier
				.padding(it)
				.verticalScroll(rememberScrollState())
		) {
			ConvertedRatesGrid(uiState.convertedRates)
		}
	}
}

