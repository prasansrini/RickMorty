package com.rick.network.repository

import android.util.Log
import com.rick.network.models.domain.Character
import com.rick.network.models.domain.Episode
import com.rick.network.models.remote.RemoteCharacter
import com.rick.network.models.remote.RemoteEpisode
import com.rick.network.models.remote.toDomainCharacter
import com.rick.network.models.remote.toDomainEpisode
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

	private var characterCache = mutableMapOf<Int, Character>()

	private var episodeCache = mutableMapOf<Int, Episode>()

	override suspend fun getCharacter(id: Int): ApiOperation<Character> {
		characterCache[id]?.let {
			return ApiOperation.Success(it)
		}

		return safeApiCall {
			client
				.get("character/$id")
				.body<RemoteCharacter>()
				.toDomainCharacter()
				.also {
					characterCache[id] = it
				}
		}
	}

	override suspend fun getEpisodes(episodeIds: List<Int>): ApiOperation<List<Episode>> {
		val idsCommaSeparated = episodeIds.joinToString(separator = ",")

		return safeApiCall {
			client
				.get("episode/$idsCommaSeparated")
				.body<List<RemoteEpisode>>()
				.map {
					Log.e(
						"EPISODE_DEBUG",
						it.toString()
					)
					it.toDomainEpisode()
				}
		}
	}

	private inline fun <T> safeApiCall(apiCall: () -> T): ApiOperation<T> {
		return try {
			ApiOperation.Success(data = apiCall())
		} catch (e: Exception) {
			ApiOperation.Failure(e)
		}
	}
}

sealed interface ApiOperation<T> {
	data class Success<T>(val data: T) : ApiOperation<T>
	data class Failure<T>(val exception: Exception) : ApiOperation<T>

	fun onSuccess(block: (T) -> Unit): ApiOperation<T> {
		if (this is Success) block(data)
		return this
	}

	fun onFailure(block: (Exception) -> Unit): ApiOperation<T> {
		if (this is Failure) block(exception)
		return this
	}
}
