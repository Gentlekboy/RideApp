package com.gentlekboy.rideapp.repository

import com.gentlekboy.rideapp.model.data.RidesData
import com.gentlekboy.rideapp.model.data.UserData
import retrofit2.Response

interface HomeRepositoryInterface {
    /**
     * Fetch ride data from the server
     */
    suspend fun fetchRideData(): Response<RidesData>

    /**
     * Fetch user data from the server
     */
    suspend fun fetchUserData(): Response<UserData>
}