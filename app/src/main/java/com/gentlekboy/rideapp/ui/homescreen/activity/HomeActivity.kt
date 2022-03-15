package com.gentlekboy.rideapp.ui.homescreen.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gentlekboy.rideapp.databinding.ActivityHomeBinding
import com.gentlekboy.rideapp.model.data.Status
import com.gentlekboy.rideapp.ui.homescreen.ViewPagerAdapter
import com.gentlekboy.rideapp.ui.homescreen.fragment.NearestFragment
import com.gentlekboy.rideapp.ui.homescreen.fragment.PastFragment
import com.gentlekboy.rideapp.ui.homescreen.fragment.UpcomingFragment
import com.gentlekboy.rideapp.viewmodel.RideViewModel
import com.gentlekboy.rideapp.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val rideViewModel: RideViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewPager()
        setUpTabLayout()
        fetchRideData()
        displayUserInfo()
    }

    private fun displayUserInfo() {
        userViewModel.fetchUserData()
        userViewModel.userLivedata.observe(this) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    val userInfo = response.data

                    if (userInfo != null) {
                        binding.apply {
                            userName.text = userInfo.name
                            Glide.with(this@HomeActivity).load(userInfo.url).into(userImage)
                        }
                    }
                }
                Status.ERROR -> {
                    Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_LONG).show()
                }
                Status.LOADING -> {}
            }

        }
    }

    private fun fetchRideData() {
        rideViewModel.fetchRideData()
    }

    private fun setUpTabLayout() {
        binding.apply {
            viewPager.adapter = viewPagerAdapter
            tabLayout.setupWithViewPager(binding.viewPager)
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {}
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun setUpViewPager() {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.apply {
            addFragment(NearestFragment(), "Nearest")
            addFragment(UpcomingFragment(), "Upcoming")
            addFragment(PastFragment(), "Past")
        }
    }
}