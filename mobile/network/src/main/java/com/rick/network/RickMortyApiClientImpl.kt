package com.rick.network

import android.util.Log
import com.rick.network.model.Character
import com.rick.network.repository.RickMortyApiClient
import com.rick.network.util.Constants.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class RickMortyApiClientImpl : RickMortyApiClient {
	private val client = HttpClient(OkHttp) {
		defaultRequest { url(BASE_URL) }

		install(Logging) {
			logger = Logger.SIMPLE
		}

		install(ContentNegotiation) {
			json(Json {
				ignoreUnknownKeys = true
			})
		}
	}

	override suspend fun getCharacter(id: Int): Character {
		val character: Character = client
			.get("character/$id")
			.body()

		Log.e(
			"CHARACTER_DEBUG",
			character.toString()
		)

		return character
	}
}
