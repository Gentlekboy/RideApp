package com.gentlekboy.rideapp.di

import com.gentlekboy.rideapp.model.network.ApiInterface
import com.gentlekboy.rideapp.repository.HomeRepository
import com.gentlekboy.rideapp.repository.HomeRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * This class provides an instance of [HomeRepositoryInterface] for dependency injection
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    /**
     * Provides an instance of [HomeRepositoryInterface] for dependency injection
     */
    @Singleton
    @Provides
    fun provideRepository(apiInterface: ApiInterface): HomeRepositoryInterface {
        return HomeRepository(apiInterface)
    }
}