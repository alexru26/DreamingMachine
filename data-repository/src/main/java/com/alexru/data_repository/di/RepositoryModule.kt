package com.alexru.data_repository.di

import com.alexru.data_repository.repository.DiscographyRepositoryImpl
import com.alexru.domain.repository.DiscographyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * DI Module that provides [DiscographyRepository]
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDiscographyRepository(
        discographyRepositoryImpl: DiscographyRepositoryImpl
    ): DiscographyRepository
}