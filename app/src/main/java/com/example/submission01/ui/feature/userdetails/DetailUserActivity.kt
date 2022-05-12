package com.example.submission01.ui.feature.userdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submission01.R
import com.example.submission01.databinding.ActivityDetailUserBinding
import com.example.submission01.domain.model.User
import com.example.submission01.ui.adapter.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailUserViewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)

        initView()
        initData()
    }

    private fun initView() {
        val actionbar = supportActionBar
        actionbar!!.title = ""
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        detailUserViewModel.userDetail.observe(this) { setUserDetails(it) }
        detailUserViewModel.isLoading.observe(this) { showLoading(it) }
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        detailUserViewModel.getuserDetail(user.username)
    }

    private fun setUserDetails(user: User) {
        Glide.with(this).load(user.avatar).into(binding.imgPhoto)
        binding.txtUsername.text = user.username
        binding.txtName.text = user.name
        binding.txtOfficeAndLocation.text = "${user.company}, ${user.location}"

        binding.viewPager.adapter = SectionsPagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> { tab.text = "Followers ${user.follower}"}
                1 -> { tab.text = "Following ${user.following}"}
            }
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarLayout.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}