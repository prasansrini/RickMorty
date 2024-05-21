package com.rick.network.repository

import com.rick.network.models.domain.Character
import com.rick.network.models.remote.RemoteCharacter
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

	override suspend fun getCharacter(id: Int): ApiOperation<Character> {
		return safeApiCall {
			client
				.get("character/$id")
				.body<RemoteCharacter>()
				.toDomainCharacter()
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
