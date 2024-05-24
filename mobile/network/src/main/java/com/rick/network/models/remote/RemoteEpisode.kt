package com.rick.network.models.remote

import com.rick.network.models.domain.Episode
import kotlinx.serialization.Serializable

@Serializable
data class RemoteEpisode(
		val id: Int, val name: String, val episode: String, val air_date: String, val character: List<String>
)

fun RemoteEpisode.toDomainEpisode(): Episode {
	return Episode(id = id,
		name = name,
		seasonNumber = episode
			.filter { it.isDigit() }
			.take(2)
			.toInt(),
		episodeNumber = episode
			.filter { it.isDigit() }
			.takeLast(2)
			.toInt(),
		airDate = air_date,
		characterIdsInEpisode = character.map {
			it
				.substring(startIndex = it.lastIndexOf("/") + 1)
				.toInt()
		})
}
