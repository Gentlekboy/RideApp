package com.gentlekboy.rideapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gentlekboy.rideapp.model.data.Resource
import com.gentlekboy.rideapp.model.data.RidesDataItem
import com.gentlekboy.rideapp.model.data.UserData
import com.gentlekboy.rideapp.repository.HomeRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepositoryInterface: HomeRepositoryInterface
) : ViewModel() {

    private val _rideLivedata: MutableLiveData<Resource<ArrayList<RidesDataItem>>> =
        MutableLiveData()
    val rideLivedata: LiveData<Resource<ArrayList<RidesDataItem>>> = _rideLivedata

    fun fetchRideData() {
        _rideLivedata.postValue(Resource.loading())

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val ridesData = homeRepositoryInterface.fetchRideData()

                when (ridesData.isSuccessful) {
                    true -> _rideLivedata.postValue(
                        Resource.success(
                            ridesData.body(),
                            "Successful"
                        )
                    )
                    else -> _rideLivedata.postValue(Resource.error(ridesData.message()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _rideLivedata.postValue(Resource.error("Something went wrong"))
            }
        }
    }

    private val _userLivedata: MutableLiveData<Resource<UserData>> = MutableLiveData()
    val userLivedata: LiveData<Resource<UserData>> = _userLivedata

    fun fetchUserData() {
        _userLivedata.postValue(Resource.loading())

        viewModelScope.launch(Dispatchers.IO) {
            val userData = homeRepositoryInterface.fetchUserData()

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