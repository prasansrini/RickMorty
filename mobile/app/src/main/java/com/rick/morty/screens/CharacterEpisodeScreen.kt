package com.rick.morty.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rick.morty.feature_ram.components.common.CharacterImage
import com.rick.morty.feature_ram.components.common.CharacterNameComponent
import com.rick.morty.feature_ram.components.common.LoadingState
import com.rick.morty.feature_ram.components.episode.EpisodeRowComponent
import com.rick.morty.feature_ram.ui.theme.RickPrimary
import com.rick.morty.feature_ram.ui.theme.RickSurface
import com.rick.morty.feature_ram.ui.theme.RickTextPrimary
import com.rick.network.models.domain.Character
import com.rick.network.models.domain.Episode
import com.rick.network.repository.RickMortyApiClient
import kotlinx.coroutines.launch

@Composable
fun CharacterEpisodeScreen(characterId: Int, client: RickMortyApiClient) {
	var characterState by remember {
		mutableStateOf<Character?>(null)
	}

	var episodesState by remember {
		mutableStateOf<List<Episode>>(emptyList())
	}

	LaunchedEffect(key1 = Unit) {
		client
			.getCharacter(characterId)
			.onSuccess { character ->
				characterState = character

				launch {
					client
						.getEpisodes(character.episodeIds)
						.onSuccess { episodeList ->
							episodesState = episodeList
						}
						.onFailure { exception ->
							exception.localizedMessage?.let { message ->
								Log.e(
									"EPISODE_DEBUG",
									message
								)
							}
						}
				}
			}
			.onFailure { exception ->
				exception.localizedMessage?.let { message ->
					Log.e(
						"EPISODE_DEBUG",
						message
					)
				}
			}
	}

	characterState?.let {
		MainScreen(
			character = it,
			episodes = episodesState
		)
	} ?: LoadingState()
}

@Composable
fun MainScreen(character: Character, episodes: List<Episode>) {
	LazyColumn(
		contentPadding = PaddingValues(all = 16.dp),
		modifier = Modifier.background(color = RickPrimary)
	) {
		item { CharacterNameComponent(name = character.name) }
		item { Spacer(modifier = Modifier.height(16.dp)) }
		item { CharacterImage(imageUrl = character.imageUrl) }

		episodes
			.groupBy { it.seasonNumber }
			.forEach { mapEntry ->

				item { SeasonNumber(seasonNumber = mapEntry.key) }

				items(mapEntry.value) { episode ->
					EpisodeRowComponent(episode = episode)
				}
			}
	}
}

@Composable
fun SeasonNumber(seasonNumber: Int) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(
				top = 8.dp,
				bottom = 16.dp
			)
	) {
		Text(
			text = "Season $seasonNumber",
			fontSize = 32.sp,
			color = RickTextPrimary,
			fontStyle = FontStyle.Italic,
			fontFamily = FontFamily.Cursive,
			textAlign = TextAlign.Center,
			modifier = Modifier
				.fillMaxWidth()
				.border(
					width = 1.dp,
					color = RickSurface,
					shape = RoundedCornerShape(8.dp)
				)
				.padding(vertical = 4.dp)
		)
	}
}
