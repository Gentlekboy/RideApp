package com.gentlekboy.rideapp.di

import com.gentlekboy.rideapp.model.network.ApiInterface
import com.gentlekboy.rideapp.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * This class provides an instance of [Retrofit] and [ApiInterface] for dependency injection
 */
@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    /**
     * Provides an instance of [Retrofit]
     */
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
    }

    /**
     * Provides an instance of [ApiInterface]
     */
    @Singleton
    @Provides
    fun provideApiInterface(): ApiInterface {
        return provideRetrofit().create(ApiInterface::class.java)
    }
}