package com.gentlekboy.rideapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gentlekboy.rideapp.model.data.Resource
import com.gentlekboy.rideapp.model.data.UserData
import com.gentlekboy.rideapp.repository.user.UserRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepositoryInterface: UserRepositoryInterface
) : ViewModel() {

    private val _userLivedata: MutableLiveData<Resource<UserData>> = MutableLiveData()
    val userLivedata: LiveData<Resource<UserData>> = _userLivedata

    /**
     * Fetch user data from the server via the repository
     */
    fun fetchUserData() {
        _userLivedata.postValue(Resource.loading())

        viewModelScope.launch(Dispatchers.IO) {
            val userData = userRepositoryInterface.fetchUserData()

            try {
                when (userData.isSuccessful) {
                    true -> _userLivedata.postValue(Resource.success(userData.body(), "Successful"))
                    else -> _userLivedata.postValue(Resource.error(userData.message()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _userLivedata.postValue(Resource.error("Something went wrong"))
            }
        }
    }
}