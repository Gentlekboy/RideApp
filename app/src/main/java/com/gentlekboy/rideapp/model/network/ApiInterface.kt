package com.gentlekboy.rideapp.model.network

import com.gentlekboy.rideapp.model.data.RidesData
import com.gentlekboy.rideapp.model.data.UserData
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interface for making network calls
 */
interface ApiInterface {
    /**
     * Fetch a list of ride information from the server
     */
    @GET("rides")
    suspend fun fetchRideData(): Response<RidesData>

    /**
     * Fetch user information from the server
     */
    @GET("user")
    suspend fun fetchUserData(): Response<UserData>
}