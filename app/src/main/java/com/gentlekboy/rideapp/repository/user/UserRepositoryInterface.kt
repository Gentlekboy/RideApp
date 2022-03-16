package com.gentlekboy.rideapp.repository.user

import com.gentlekboy.rideapp.model.data.UserData
import retrofit2.Response

interface UserRepositoryInterface {
    /**
     * Fetch user data from the server
     */
    suspend fun fetchUserData(): Response<UserData>
}