package com.example.submission01.ui.feature.userdetails

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.submission01.databinding.ActivityDetailUserBinding
import com.example.submission01.domain.model.User

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var user: User

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        initView()
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

    private fun initView() {
        val actionbar = supportActionBar
        actionbar!!.title = ""
        actionbar.setDisplayHomeAsUpEnabled(true)

        val fileName: String = user.avatar.replace("@drawable/", "")
        val avatarId = this.resources.getIdentifier(fileName, "drawable", this.packageName)

        binding.imgPhoto.setImageResource(avatarId)
        binding.txtUsername.text = user.username
        binding.txtName.text = user.name
        binding.txtOfficeAndLocation.text = "${user.company}, ${user.location}"
        binding.txtFollowers.text = user.follower.toString()
        binding.txtFollowing.text = user.following.toString()
        binding.txtRepository.text = user.repository.toString()
    }
}