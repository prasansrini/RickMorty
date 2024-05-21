package com.rick.morty.feature_ram.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rick.morty.core.util.Constants.Routes.CHARACTER_DETAIL_ROUTE
import com.rick.morty.core.util.Constants.Routes.CHARACTER_EPISODE_ROUTE
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

			val navController = rememberNavController()

			NavHost(
				navController = navController,
				startDestination = "character_details"
			) {
				composable(CHARACTER_DETAIL_ROUTE) {
					HomeComponent()
				}

				composable(CHARACTER_EPISODE_ROUTE) { }
			}
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