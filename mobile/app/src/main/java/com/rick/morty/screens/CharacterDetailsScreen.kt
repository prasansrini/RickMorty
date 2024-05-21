package com.rick.morty.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rick.morty.feature_ram.components.character.CharacterDetailsNamePlateComponent
import com.rick.morty.feature_ram.components.common.DataPoint
import com.rick.morty.feature_ram.components.common.DataPointComponent
import com.rick.morty.feature_ram.components.common.LoadingState
import com.rick.network.models.domain.Character
import com.rick.network.repository.RickMortyApiClient
import kotlinx.coroutines.delay

@Composable
fun CharacterDetailsScreen(
		rickMortyApiClient: RickMortyApiClient, characterId: Int
) {
	var character by remember {
		mutableStateOf<Character?>(null)
	}

	val characterDataPoints: List<DataPoint> by remember {
		derivedStateOf {
			buildList {
				character?.let { character ->
					add(
						DataPoint(
							"Last known location",
							character.location.name
						)
					)
					add(
						DataPoint(
							"Species",
							character.species
						)
					)
					add(
						DataPoint(
							"Gender",
							character.gender.displayName
						)
					)

					character.type
						.takeIf { it.isNotEmpty() }
						?.let { type ->
							add(
								DataPoint(
									"Type",
									type
								)
							)
						}

					add(
						DataPoint(
							"Origin",
							character.origin.name
						)
					)

					add(
						DataPoint(
							"Episode count",
							character.episodeUrls.size.toString()
						)
					)
				}
			}
		}
	}

	LaunchedEffect(key1 = Unit,
		block = {
			delay(500)
			character = rickMortyApiClient.getCharacter(characterId)
		})

	LazyColumn(
		modifier = Modifier.fillMaxSize(),
		contentPadding = PaddingValues(all = 16.dp)
	) {
		if (character == null) {
			item { LoadingState() }
			return@LazyColumn
		}

		item {
			CharacterDetailsNamePlateComponent(
				name = character!!.name,
				status = character!!.status
			)
		}

		item {
			Spacer(modifier = Modifier.height(8.dp))
		}

		item {

		}

		items(characterDataPoints) { dataPoint ->
			Spacer(modifier = Modifier.height(32.dp))
			DataPointComponent(dataPoint = dataPoint)
		}
	}
}