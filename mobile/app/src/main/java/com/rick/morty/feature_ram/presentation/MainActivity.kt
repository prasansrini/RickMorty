package com.rick.morty.feature_ram.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rick.morty.core.util.Constants.Routes.CHARACTER_DETAIL_ROUTE
import com.rick.morty.core.util.Constants.Routes.CHARACTER_EPISODE_ROUTE
import com.rick.morty.feature_ram.ui.theme.RickAndMortyAppTheme
import com.rick.morty.screens.CharacterDetailsScreen
import com.rick.morty.screens.CharacterEpisodeScreen
import com.rick.network.repository.RickMortyApiClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	private val characterId = 2

	@Inject
	lateinit var rickMortyApiClient: RickMortyApiClient

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {

			val navController = rememberNavController()

			NavHost(
				navController = navController,
				startDestination = CHARACTER_DETAIL_ROUTE
			) {
				composable(CHARACTER_DETAIL_ROUTE) {
					HomeComponent {
						navController.navigate("$CHARACTER_EPISODE_ROUTE/$it")
					}
				}

				composable(
					route = "$CHARACTER_EPISODE_ROUTE/{characterId}",
					arguments = listOf(navArgument("characterId") { type = NavType.IntType })
				) { backStackEntry ->
					RickAndMortyAppTheme {
						Surface(modifier = Modifier.fillMaxSize()) {
							CharacterEpisodeScreen(
								characterId = backStackEntry.arguments?.getInt("characterId")
										?: -1
							)
						}
					}
				}
			}
		}
	}

	@Composable
	fun HomeComponent(onEpisodeClicked: (Int) -> Unit) {
		RickAndMortyAppTheme {
			Surface(modifier = Modifier.fillMaxSize()) {
				CharacterDetailsScreen(
					rickMortyApiClient = rickMortyApiClient,
					characterId = characterId,
					onEpisodeClicked = onEpisodeClicked
				)
			}
		}
	}
}