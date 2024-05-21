package com.rick.network.models.remote

import com.rick.network.models.domain.Character
import com.rick.network.models.domain.CharacterGender
import com.rick.network.models.domain.CharacterStatus
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCharacter(
		val created: String,
		val episode: List<String>,
		val gender: String,
		val id: Int,
		val image: String,
		val location: Location,
		val name: String,
		val origin: Origin,
		val species: String,
		val status: String,
		val type: String,
		val url: String
) {
	@Serializable
	data class Location(
			val name: String, val url: String
	)

	@Serializable
	data class Origin(
			val name: String, val url: String
	)

	fun toDomainCharacter(): Character {
		val gender = when (gender.lowercase()) {
			"female" -> CharacterGender.Female
			"male" -> CharacterGender.Male
			"genderless" -> CharacterGender.Genderless
			else -> CharacterGender.Unknown
		}

		val characterStatus = when (status.lowercase()) {
			"alive" -> CharacterStatus.Alive
			"dead" -> CharacterStatus.Dead
			else -> CharacterStatus.Unknown
		}

		return Character(
			created = created,
			episodeUrls = episode.map {
				it.substring(it.lastIndexOf("/") + 1)
			},
			gender = gender,
			id = id,
			imageUrl = image,
			location = Character.Location(
				name = location.name,
				url = origin.url
			),
			name = name,
			origin = Character.Origin(
				name = origin.name,
				url = origin.url
			),
			species = species,
			status = characterStatus,
			type = type
		)
	}
}
