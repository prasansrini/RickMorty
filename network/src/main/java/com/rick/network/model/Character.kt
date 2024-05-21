package com.rick.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Character(
		val id: Int, val name: String, val type: String, val gender: String, val origin: Origin, val image: String
) {
	override fun toString(): String {
		return "Character(id=$id, name='$name', type='$type', gender='$gender', origin=$origin, image='$image')"
	}
}