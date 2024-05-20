package com.rick.morty.feature_ram.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rick.morty.feature_ram.ui.theme.RickAndMortyAppTheme
import com.rick.network.components.TextComponent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			RickAndMortyAppTheme {
				Surface(modifier = Modifier.fillMaxSize()) {
					TextComponent(
						name = "Android",
						modifier = Modifier.padding(8.dp)
					)
				}
			}
		}
	}
}
