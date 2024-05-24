package com.rick.morty.screens

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rick.morty.feature_ram.components.common.CharacterImage
import com.rick.morty.feature_ram.components.common.CharacterNameComponent
import com.rick.morty.feature_ram.components.common.LoadingState
import com.rick.morty.feature_ram.components.episode.EpisodeRowComponent
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
	LazyColumn(contentPadding = PaddingValues(all = 16.dp)) {
		item { CharacterNameComponent(name = character.name) }
		item { Spacer(modifier = Modifier.height(16.dp)) }
		item { CharacterImage(imageUrl = character.imageUrl) }

		if (episodes.isEmpty()) {
			item {
				Text(
					text = "Empty episodes!",
					fontSize = 24.sp,
					textAlign = TextAlign.Center
				)
			}
		} else {
			items(episodes) { episode ->
				EpisodeRowComponent(episode = episode)
			}
		}

	}
}