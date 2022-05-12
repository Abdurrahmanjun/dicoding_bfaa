package com.example.submission01.ui.feature.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission01.databinding.FragmentListBinding
import com.example.submission01.domain.model.User
import com.example.submission01.ui.adapter.UserAdapter

class FollowersFollowingFragment(val user: User,val flag: String) : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var followersFollowingViewModel: FollowersFollowingViewModel

    companion object{
        const val FOLLOWERS = "followers"
        const val FOLLOWING = "following"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        followersFollowingViewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(FollowersFollowingViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(context, layoutManager.orientation)
        )
    }

    private fun initData() {
        followersFollowingViewModel.user = this.user
        followersFollowingViewModel.flag = this.flag
        followersFollowingViewModel.listUsers.observe(viewLifecycleOwner) { setFollowerList(it.toMutableList()) }
        followersFollowingViewModel.isLoading.observe(viewLifecycleOwner) { showLoading(it) }
        followersFollowingViewModel.getUsersFollower()
    }

    private fun setFollowerList(userlist: MutableList<User>) {
        val adapter = UserAdapter(this.requireContext(),null)
        adapter.users = userlist
        binding.recyclerView.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarLayout.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}