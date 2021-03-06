package com.gentlekboy.rideapp.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import com.gentlekboy.rideapp.model.data.RidesDataItem
import com.gentlekboy.rideapp.ui.homescreen.adapter.RideAdapter
import com.gentlekboy.rideapp.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.math.absoluteValue

/**
 * Get the current user's station code
 */
fun getUserStationCode(userViewModel: UserViewModel, viewLifecycleOwner: LifecycleOwner): Int {
    var userStationCode = 0

    userViewModel.userLivedata.observe(viewLifecycleOwner) {
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

/**
 * Gets all rides and sorts it by dates, from current to previous
 * @return returns an [Int] which is the number of past rides
 */
fun getAllPastRides(listOfRides: ArrayList<RidesDataItem>, rideAdapter: RideAdapter): Int {
    val listOfPastRides = arrayListOf<RidesDataItem>()

    listOfRides.forEach { ride ->
        val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm a")
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT+1:00")
        val currentTime = simpleDateFormat.format(Date())

        if (simpleDateFormat.parse(ride.date)!!.time < simpleDateFormat.parse(currentTime)!!.time) {
            listOfPastRides.add(ride)
        }
    }

    sortRidesByDate(listOfPastRides, rideAdapter)
    return listOfPastRides.size
}

/**
 * Gets all rides and sorts it by dates, from current to future/upcoming
 * @return returns an [Int] which is the number of upcoming rides
 */
fun getAllUpcomingRides(listOfRides: ArrayList<RidesDataItem>, rideAdapter: RideAdapter): Int {
    val listOfUpcomingRides = arrayListOf<RidesDataItem>()

    listOfRides.forEach { ride ->
        val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm a")
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT+1:00")
        val currentTime = simpleDateFormat.format(Date())

        if (simpleDateFormat.parse(ride.date)!!.time > simpleDateFormat.parse(currentTime)!!.time) {
            listOfUpcomingRides.add(ride)
        }
    }

    sortRidesByDate(listOfUpcomingRides, rideAdapter)
    return listOfUpcomingRides.size
}

/**
 * Sorts a ride list by date
 */
fun sortRidesByDate(listOfRidesByDate: ArrayList<RidesDataItem>, rideAdapter: RideAdapter) {
    val sortedListByPast = listOfRidesByDate.sortedWith(compareBy { it.date }).toMutableList()
    rideAdapter.addRides(sortedListByPast)
}