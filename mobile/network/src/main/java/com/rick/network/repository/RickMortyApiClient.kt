package com.rick.network.repository

import com.rick.network.models.domain.Character

interface RickMortyApiClient {
	suspend fun getCharacter(id: Int): Character
}