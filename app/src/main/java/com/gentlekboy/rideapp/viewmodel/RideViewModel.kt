package com.gentlekboy.rideapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gentlekboy.rideapp.model.data.Resource
import com.gentlekboy.rideapp.model.data.RidesDataItem
import com.gentlekboy.rideapp.repository.ride.RideRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RideViewModel @Inject constructor(
    private val rideRepositoryInterface: RideRepositoryInterface
) : ViewModel() {

    private val _rideLivedata: MutableLiveData<Resource<ArrayList<RidesDataItem>>> =
        MutableLiveData()
    val rideLivedata: LiveData<Resource<ArrayList<RidesDataItem>>> = _rideLivedata

    /**
     * Fetch ride data from the server via the repository
     */
    fun fetchRideData() {
        _rideLivedata.postValue(Resource.loading())

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val ridesData = rideRepositoryInterface.fetchRideData()

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
}