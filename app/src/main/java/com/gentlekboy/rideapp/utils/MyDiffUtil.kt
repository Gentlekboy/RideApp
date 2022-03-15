package com.gentlekboy.rideapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.gentlekboy.rideapp.model.data.RidesDataItem

/**
 * DiffUtil class for recycler view adapters
 */
class MyDiffUtil(
    private val oldList: ArrayList<RidesDataItem>,
    private val newList: ArrayList<RidesDataItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = when {
        oldList[oldItemPosition].id != newList[newItemPosition].id -> false
        oldList[oldItemPosition].city != newList[newItemPosition].city -> false
        oldList[oldItemPosition].state != newList[newItemPosition].state -> false
        oldList[oldItemPosition].date != newList[newItemPosition].date -> false
        oldList[oldItemPosition].destination_station_code != newList[newItemPosition].destination_station_code -> false
        oldList[oldItemPosition].origin_station_code != newList[newItemPosition].origin_station_code -> false
        oldList[oldItemPosition].map_url != newList[newItemPosition].map_url -> false
        oldList[oldItemPosition].station_path != newList[newItemPosition].station_path -> false
        else -> true
    }
}