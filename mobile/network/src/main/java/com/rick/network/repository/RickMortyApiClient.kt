package com.rick.network.repository

import com.rick.network.models.domain.Character
import com.rick.network.models.domain.Episode

interface RickMortyApiClient {
	suspend fun getCharacter(id: Int): ApiOperation<Character>

	suspend fun getEpisodes(episodeIds: List<Int>): ApiOperation<List<Episode>>
}