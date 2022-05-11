package com.example.submission01.ui.feature.userdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submission01.R
import com.example.submission01.databinding.ActivityDetailUserBinding
import com.example.submission01.domain.model.User

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel

    companion object {
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following,
        )
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
        detailUserViewModel.user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        if (detailUserViewModel.user.id == 0) {
            val fileName: String = detailUserViewModel.user.avatar.replace("@drawable/", "")
            val avatarId =
                this.resources.getIdentifier(fileName, "drawable", this.packageName)
            binding.imgPhoto.setImageResource(avatarId)
        }else {
            Glide.with(this).load(detailUserViewModel.user.avatar).into(binding.imgPhoto)
        }

        binding.txtUsername.text = detailUserViewModel.user.username
        binding.txtName.text = detailUserViewModel.user.name
        binding.txtOfficeAndLocation.text = "${detailUserViewModel.user.company}, ${detailUserViewModel.user.location}"
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

}