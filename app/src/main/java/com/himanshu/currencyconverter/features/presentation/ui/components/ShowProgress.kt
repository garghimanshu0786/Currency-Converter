package com.himanshu.currencyconverter.features.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ShowProgress(showProgress: Boolean = true, modifier: Modifier = Modifier.fillMaxSize()) {
	if (showProgress) {
		Box(modifier = modifier
			.fillMaxSize()
			.clickable(
				indication = null,
				interactionSource = remember { MutableInteractionSource() }) {},
			contentAlignment = Alignment.Center
		) {
			CircularProgressIndicator(color = MaterialTheme.colorScheme.inversePrimary)
		}
	}
}