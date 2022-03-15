package com.gentlekboy.rideapp.ui.homescreen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gentlekboy.rideapp.databinding.FragmentPastBinding
import com.gentlekboy.rideapp.model.data.Status
import com.gentlekboy.rideapp.ui.homescreen.adapter.RideAdapter
import com.gentlekboy.rideapp.utils.getAllPastRides
import com.gentlekboy.rideapp.utils.getDistance
import com.gentlekboy.rideapp.utils.getUserStationCode
import com.gentlekboy.rideapp.viewmodel.RideViewModel
import com.gentlekboy.rideapp.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PastFragment : Fragment() {
    private var _binding: FragmentPastBinding? = null
    private val binding get() = _binding!!
    private val rideAdapter by lazy { RideAdapter(requireContext()) }
    private val rideViewModel: RideViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
    }

    private fun setUpAdapter() {
        binding.pastRecyclerView.adapter = rideAdapter
        rideViewModel.rideLivedata.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    val listOfRides = response.data

                    if (listOfRides != null) {
                        val userStationCode = getUserStationCode(userViewModel, viewLifecycleOwner)
                        getDistance(listOfRides, userStationCode)
                        getAllPastRides(listOfRides, rideAdapter)
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