package com.gentlekboy.rideapp.ui.homescreen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gentlekboy.rideapp.databinding.FragmentNearestBinding
import com.gentlekboy.rideapp.model.data.Status
import com.gentlekboy.rideapp.ui.homescreen.adapter.RideAdapter
import com.gentlekboy.rideapp.utils.getDistance
import com.gentlekboy.rideapp.utils.getUserStationCode
import com.gentlekboy.rideapp.utils.sortRides
import com.gentlekboy.rideapp.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NearestFragment : Fragment() {
    private var _binding: FragmentNearestBinding? = null
    private val binding get() = _binding!!
    private val rideAdapter by lazy { RideAdapter(requireContext()) }
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNearestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
    }

    /**
     * Set up adapter to display ride information by nearest
     */
    private fun setUpAdapter() {
        binding.nearestRecyclerView.adapter = rideAdapter
        homeViewModel.rideLivedata.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    val listOfRides = response.data

                    if (listOfRides != null) {
                        val userStationCode = getUserStationCode(homeViewModel, viewLifecycleOwner)
                        getDistance(listOfRides, userStationCode)
                        sortRides(listOfRides, rideAdapter)
                    }
                }
                Status.LOADING -> {}
                Status.ERROR -> {
                    Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}