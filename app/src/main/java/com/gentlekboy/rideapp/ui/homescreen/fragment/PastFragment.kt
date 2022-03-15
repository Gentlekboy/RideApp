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
import com.gentlekboy.rideapp.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PastFragment : Fragment() {
    private var _binding: FragmentPastBinding? = null
    private val binding get() = _binding!!
    private val rideAdapter by lazy { RideAdapter(requireContext()) }
    private val homeViewModel: HomeViewModel by activityViewModels()

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
        homeViewModel.rideLivedata.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    val listOfRides = response.data

                    if (listOfRides != null) {
                        rideAdapter.addRides(listOfRides)
                    }
                }
                else -> {
                    Snackbar.make(binding.root, "Check past fragment", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}