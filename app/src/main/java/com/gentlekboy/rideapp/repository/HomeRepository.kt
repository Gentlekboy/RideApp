package com.gentlekboy.rideapp.repository

import com.gentlekboy.rideapp.model.data.RidesData
import com.gentlekboy.rideapp.model.data.UserData
import com.gentlekboy.rideapp.model.network.ApiInterface
import retrofit2.Response
import javax.inject.Inject

/**
 * This class implements the [HomeRepositoryInterface] interface to fetch ride and user data from the server
 */
class HomeRepository @Inject constructor(
    private val apiInterface: ApiInterface
) : HomeRepositoryInterface {

    override suspend fun fetchRideData(): Response<RidesData> {
        return apiInterface.fetchRideData()
    }

    override suspend fun fetchUserData(): Response<UserData> {
        return apiInterface.fetchUserData()
    }
}