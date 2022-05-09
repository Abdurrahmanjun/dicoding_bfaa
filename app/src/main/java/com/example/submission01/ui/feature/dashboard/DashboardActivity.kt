package com.example.submission01.ui.feature.dashboard

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.submission01.R
import com.example.submission01.databinding.ActivityMainBinding
import com.example.submission01.ui.adapter.UserAdapter
import com.example.submission01.ui.feature.userdetails.DetailUserActivity
import com.example.submission01.ui.feature.userdetails.DetailUserActivity.Companion.EXTRA_USER


class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dashboardViewModel: DashboardViewModel

    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dashboardViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DashboardViewModel::class.java)

        initView()
        initData()
    }

    private fun initView() {
        adapter = UserAdapter(this)
        binding.listView.adapter = adapter
        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val toUserDetails = Intent(this@DashboardActivity, DetailUserActivity::class.java)
            toUserDetails.putExtra(EXTRA_USER, dashboardViewModel.users[position])
            startActivity(toUserDetails)
        }
    }

    private fun initData() {
        adapter.users = dashboardViewModel.parseJSON(this.assets.open("githubuser.json"))

        dashboardViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.dashboard_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        val searchMenuItem = menu.findItem(R.id.search) as MenuItem

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@DashboardActivity, query, Toast.LENGTH_SHORT).show()
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })

        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                adapter.users.clear()
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                adapter.users = dashboardViewModel.parseJSON(this@DashboardActivity.assets.open("githubuser.json"))
                return true
            }
        })

        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}