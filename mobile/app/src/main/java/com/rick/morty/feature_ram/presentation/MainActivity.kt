package com.rick.morty.feature_ram.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.rick.morty.feature_ram.ui.theme.RickAndMortyAppTheme
import com.rick.morty.screens.CharacterDetailsScreen
import com.rick.network.models.domain.Character
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
			var character by remember {
				mutableStateOf<Character?>(null)
			}

			LaunchedEffect(key1 = Unit,
				block = {
					character = rickMortyApiClient.getCharacter(65)
				})

			RickAndMortyAppTheme {
				Surface(modifier = Modifier.fillMaxSize()) {
					CharacterDetailsScreen(
						rickMortyApiClient = rickMortyApiClient,
						characterId = 25
					)
				}
			}
		}
	}
}
