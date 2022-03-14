package com.gentlekboy.rideapp.model.data

/**
 * Data class showing fields of [RidesDataItem]
 */
data class RidesDataItem(
    val id: Int,
    val origin_station_code: Int,
    val map_url: String,
    val destination_station_code: Int,
    val station_path: ArrayList<Int>,
    val date: String,
    val city: String,
    val state: String
)
