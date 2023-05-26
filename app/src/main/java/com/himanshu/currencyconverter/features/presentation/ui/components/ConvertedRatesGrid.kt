package com.himanshu.currencyconverter.features.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ConvertedRatesGrid(currencyRate: Map<String, Double>) {
	AnimatedVisibility(visible = currencyRate.any()) {
		FlowRow(
			modifier = Modifier.padding(top = 20.dp),
			horizontalArrangement = Arrangement.SpaceEvenly,
		) {
			currencyRate.forEach {
				Column(
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.CenterHorizontally,
					modifier = Modifier
						.padding(10.dp)
						.size(115.dp)
						.clip(RoundedCornerShape(4.dp))
						.border(
							width = 1.dp,
							color = MaterialTheme.colorScheme.tertiary,
							shape = RoundedCornerShape(4.dp)
						)
				) {
					Box(
						modifier = Modifier
							.weight(1f)
							.fillMaxWidth()
							.background(MaterialTheme.colorScheme.primary)
					) {
						Text(
							color = Color.White,
							modifier = Modifier.align(Alignment.Center),
							text = it.key
						)
					}

					Box(
						modifier = Modifier
							.weight(1f)
							.fillMaxWidth()
							.background(MaterialTheme.colorScheme.secondary)
					) {
						Text(
							modifier = Modifier
								.align(Alignment.Center)
								.padding(top = 5.dp),
							color = Color.White,
							text = it.value.toString(),
							textAlign = TextAlign.Center
						)
					}
				}
			}
		}
	}
}

