package com.gentlekboy.rideapp.ui.homescreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gentlekboy.rideapp.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.apply {
            addFragment(NearestFragment(), "Nearest")
            addFragment(UpcomingFragment(), "Upcoming")
            addFragment(PastFragment(), "Past")
        }

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
}