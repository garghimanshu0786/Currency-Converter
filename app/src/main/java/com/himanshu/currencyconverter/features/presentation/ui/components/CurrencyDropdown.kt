package com.himanshu.currencyconverter.features.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.himanshu.currencyconverter.R

@Composable
fun CurrencyDropdown(
	currencies: List<String>,
	selectedCurrency: String?,
	onSelectionChanged: (String) -> Unit,
) {
	var expanded by remember { mutableStateOf(false) }
	var selectedText by remember(selectedCurrency) { mutableStateOf(selectedCurrency.orEmpty()) }

	val icon = if (expanded) Icons.Filled.KeyboardArrowUp
	else Icons.Filled.KeyboardArrowDown

	Column(
		Modifier
			.padding(top = 20.dp, end = 20.dp)
			.width(110.dp)
	) {
		Box {
			TextField(onValueChange = {},
				value = selectedText,
				trailingIcon = { Icon(icon, stringResource(id = R.string.see_all_currencies)) })
			Spacer(modifier = Modifier
				.matchParentSize()
				.clickable {
					if (currencies.any()) expanded = !expanded
				}
				.padding(start = 20.dp, end = 20.dp, top = 16.dp)
				.background(Color.Transparent)

			)
		}

		DropdownMenu(
			modifier = Modifier
				.wrapContentWidth()
				.heightIn(0.dp, 400.dp),
			expanded = expanded,
			onDismissRequest = { expanded = false },
		) {
			currencies.forEach {
				DropdownMenuItem(onClick = {
					expanded = false
					selectedText = it
					onSelectionChanged(it)
				}, text = { Text(text = it) })
			}
		}
	}
}