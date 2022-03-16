package com.gentlekboy.rideapp.ui.homescreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {
    var fragmentList = ArrayList<Fragment>()
    var titleList = ArrayList<String>()

    override fun getItem(position: Int) = fragmentList[position]
    override fun getPageTitle(position: Int) = titleList[position]
    override fun getCount() = fragmentList.size

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }
}