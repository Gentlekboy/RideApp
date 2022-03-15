package com.gentlekboy.rideapp.repository.user

import com.gentlekboy.rideapp.model.network.ApiInterface
import javax.inject.Inject

/**
 * This class implements the [UserRepositoryInterface] interface to fetch ride and user data from the server
 */
class UserRepository @Inject constructor(
    private val apiInterface: ApiInterface
) : UserRepositoryInterface {
    override suspend fun fetchUserData() = apiInterface.fetchUserData()
}