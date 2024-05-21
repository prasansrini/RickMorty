package com.rick.network.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TextComponent(name: String, modifier: Modifier) {
	Text(
		text = "$name!",
		modifier = modifier
	)
}