package com.example.submission01.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission01.domain.model.User
import com.example.submission01.ui.feature.userdetails.FollowersFragment
import com.example.submission01.ui.feature.userdetails.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity,var user: User) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment(user)
            1 -> fragment = FollowingFragment(user)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}