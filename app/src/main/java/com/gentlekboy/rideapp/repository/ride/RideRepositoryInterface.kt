package com.gentlekboy.rideapp.repository.ride

import com.gentlekboy.rideapp.model.data.RidesData
import retrofit2.Response

interface RideRepositoryInterface {
    /**
     * Fetch ride data from the server
     */
    suspend fun fetchRideData(): Response<RidesData>
}