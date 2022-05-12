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

class FollowersFragment(val user: User) : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var followersViewModel: FollowersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        followersViewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
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
        followersViewModel.user = this.user
        followersViewModel.listUsers.observe(viewLifecycleOwner) { setFollowerList(it.toMutableList()) }
        followersViewModel.isLoading.observe(viewLifecycleOwner) { showLoading(it) }
        followersViewModel.getUsersFollower()
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