package com.rick.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Character(
		private val id: Int,
		private val name: String,
		private val type: String,
		private val gender: String,
		private val origin: Origin,
		private val image: String
)