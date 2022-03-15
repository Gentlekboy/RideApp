package com.gentlekboy.rideapp.repository.ride

import com.gentlekboy.rideapp.model.network.ApiInterface
import javax.inject.Inject

/**
 * This class implements the [RideRepositoryInterface] interface to fetch ride and user data from the server
 */
class RideRepository @Inject constructor(
    private val apiInterface: ApiInterface
) : RideRepositoryInterface {

    override suspend fun fetchRideData() = apiInterface.fetchRideData()
}