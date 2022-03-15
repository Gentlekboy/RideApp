package com.gentlekboy.rideapp.utils

import androidx.lifecycle.LifecycleOwner
import com.gentlekboy.rideapp.model.data.RidesDataItem
import com.gentlekboy.rideapp.ui.homescreen.adapter.RideAdapter
import com.gentlekboy.rideapp.viewmodel.HomeViewModel
import kotlin.math.absoluteValue

/**
 * Get the current user's station code
 */
fun getUserStationCode(homeViewModel: HomeViewModel, viewLifecycleOwner: LifecycleOwner): Int {
    var userStationCode = 0

    homeViewModel.userLivedata.observe(viewLifecycleOwner) {
        if (it.data != null) {
            userStationCode = it.data.station_code
        }
    }

    return userStationCode
}

/**
 * Calculates the distance and appends the value to the distance field
 */
fun getDistance(listOfRides: ArrayList<RidesDataItem>, userStationCode: Int) {
    for (ride in listOfRides) {
        var distance = Integer.MAX_VALUE
        for (path in ride.station_path) distance = distance.coerceAtMost(
            (path - userStationCode).absoluteValue
        )

        ride.distance = distance
    }
}

/**
 * Sorts [listOfRides] by distance to determine the nearest ride
 */
fun sortRides(listOfRides: ArrayList<RidesDataItem>, rideAdapter: RideAdapter) {
    val sortedListByNearest = listOfRides.sortedWith(compareBy { it.distance }).toMutableList()
    rideAdapter.addRides(sortedListByNearest)
}