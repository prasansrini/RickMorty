package com.rick.morty.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rick.morty.feature_ram.components.common.CharacterImage
import com.rick.morty.feature_ram.components.common.CharacterNameComponent
import com.rick.morty.feature_ram.components.common.LoadingState
import com.rick.network.models.domain.Character
import com.rick.network.repository.RickMortyApiClient

@Composable
fun CharacterEpisodeScreen(characterId: Int, client: RickMortyApiClient) {
	var characterState by remember {
		mutableStateOf<Character?>(null)
	}

	LaunchedEffect(key1 = Unit) {
		client
			.getCharacter(characterId)
			.onSuccess { character ->
				characterState = character
			}
			.onFailure { }
	}

	characterState?.let {
		MainScreen(character = it)
	} ?: LoadingState()
}

@Composable
fun MainScreen(character: Character) {
	LazyColumn(contentPadding = PaddingValues(all = 16.dp)) {
		item { CharacterNameComponent(name = character.name) }
		item { Spacer(modifier = Modifier.height(16.dp)) }
		item { CharacterImage(imageUrl = character.imageUrl) }
	}
}