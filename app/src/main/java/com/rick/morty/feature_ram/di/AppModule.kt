package com.rick.morty.feature_ram.di

import com.rick.network.RickMortyApiClientImpl
import com.rick.network.repository.RickMortyApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	@Provides
	@Singleton
	fun getKtorClientInstance(): RickMortyApiClient {
		return RickMortyApiClientImpl()
	}
}