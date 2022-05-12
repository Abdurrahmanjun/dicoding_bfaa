package com.example.submission01.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission01.domain.model.User
import com.example.submission01.ui.feature.userdetails.FollowersFollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity,var user: User) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFollowingFragment(user,"followers")
            1 -> fragment = FollowersFollowingFragment(user,"following")
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}