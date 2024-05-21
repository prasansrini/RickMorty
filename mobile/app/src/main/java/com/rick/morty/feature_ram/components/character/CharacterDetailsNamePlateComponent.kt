package com.rick.morty.feature_ram.components.character

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rick.morty.feature_ram.components.common.CharacterNameComponent
import com.rick.morty.screens.CharacterStatusComponent
import com.rick.network.models.domain.CharacterStatus

@Composable
fun CharacterDetailsNamePlateComponent(name: String, status: CharacterStatus) {
	Column(modifier = Modifier.fillMaxSize()) {
		CharacterStatusComponent(characterStatus = status)
		CharacterNameComponent(name = name)
	}
}