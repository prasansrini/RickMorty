package com.rick.network.repository

import com.rick.network.model.Character

interface RickMortyApiClient {
	suspend fun getCharacter(id: Int): Character
}