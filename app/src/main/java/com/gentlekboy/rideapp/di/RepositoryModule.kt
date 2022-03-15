package com.gentlekboy.rideapp.di

import com.gentlekboy.rideapp.model.network.ApiInterface
import com.gentlekboy.rideapp.repository.ride.RideRepository
import com.gentlekboy.rideapp.repository.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * This class provides an instance of [RideRepository] and [UserRepository] for injection
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    /**
     * Provides an instance of [RideRepository] for injection
     */
    @Singleton
    @Provides
    fun provideRideRepository(apiInterface: ApiInterface) = RideRepository(apiInterface)

    /**
     * Provides an instance of [UserRepository] for dependency injection
     */
    @Singleton
    @Provides
    fun provideUserRepository(apiInterface: ApiInterface) = UserRepository(apiInterface)
}