package com.rick.morty.feature_ram.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rick.morty.feature_ram.ui.theme.RickAndMortyAppTheme
import com.rick.morty.screens.CharacterDetailsScreen
import com.rick.network.repository.RickMortyApiClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	@Inject
	lateinit var rickMortyApiClient: RickMortyApiClient

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			HomeComponent()
		}
	}

	@Composable
	fun HomeComponent(modifier: Modifier = Modifier) {
		RickAndMortyAppTheme {
			Surface(modifier = Modifier.fillMaxSize()) {
				CharacterDetailsScreen(
					rickMortyApiClient = rickMortyApiClient,
					characterId = (0..600).random()
				)
			}
		}
	}
}