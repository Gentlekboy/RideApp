package com.gentlekboy.rideapp.ui.homescreen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gentlekboy.rideapp.R
import com.gentlekboy.rideapp.databinding.RideViewHolderBinding
import com.gentlekboy.rideapp.model.data.RidesDataItem
import com.gentlekboy.rideapp.utils.MyDiffUtil

/**
 * Recycler view adapter for fragments in the view pager on the Home Screen.
 * Makes use of [MyDiffUtil] for updating data.
 */
class RideAdapter(private val context: Context) :
    RecyclerView.Adapter<RideAdapter.RideViewHolder>() {

    //List holding rides currently being shown on the recycler view
    private var oldRideList = mutableListOf<RidesDataItem>()

    //Create view holder class using view binding
    inner class RideViewHolder(val binding: RideViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root)

    //Inflate the view holder layout in the recycler view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RideViewHolder(
        RideViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    //Bound data from the RideDataItem data class to views in the view holder
    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        with(holder) {
            with(oldRideList[position]) {
                with(binding) {
                    cityName.text = city
                    stateName.text = state
                    rideId.text = context.getString(R.string.ride_id, id)
                    originStation.text =
                        context.getString(R.string.origin_station, origin_station_code)
                    stationPath.text = context.getString(R.string.station_path, station_path)
                    dateTv.text = context.getString(R.string.date, date)
                    distanceTv.text = context.getString(R.string.distance, distance)
                    Glide.with(context).load(map_url).into(mapImg)
                }
            }
        }
    }

    //Notify the recycler view of the size of the items to be passed in the recycler view
    override fun getItemCount() = oldRideList.size

    /**
     * Adds a new list of movies to the adapter using the [DiffUtil] algorithm for optimization
     */
    fun addRides(newRideList: MutableList<RidesDataItem>) {
        val diffUtilLists = MyDiffUtil(oldRideList, newRideList)
        val diffResult = DiffUtil.calculateDiff(diffUtilLists)
        oldRideList = newRideList
        diffResult.dispatchUpdatesTo(this)
    }
}