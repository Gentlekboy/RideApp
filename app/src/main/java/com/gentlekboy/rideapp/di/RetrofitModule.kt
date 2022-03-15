package com.gentlekboy.rideapp.di

import com.gentlekboy.rideapp.model.network.ApiInterface
import com.gentlekboy.rideapp.utils.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
     * Provides an instance of [Gson]
     */
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    /**
     * Provides an instance of [Retrofit.Builder]
     */
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient.build())
    }

    /**
     * Provides an instance of [ApiInterface]
     */
    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit.Builder): ApiInterface {
        return retrofit.build().create(ApiInterface::class.java)
    }
}