package com.example.submission01.ui.feature.dashboard

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission01.R
import com.example.submission01.data.local.datastore.SettingPreferences
import com.example.submission01.databinding.ActivityMainBinding
import com.example.submission01.domain.model.User
import com.example.submission01.ui.adapter.UserAdapter
import com.example.submission01.ui.feature.userdetails.DetailUserActivity
import com.example.submission01.ui.feature.userdetails.DetailUserActivity.Companion.EXTRA_USER

class DashboardActivity : AppCompatActivity(), UserAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dashboardViewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(DashboardViewModel::class.java)

        initView()
        initData()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this,
            layoutManager.orientation))
    }

    private fun initData() {
        dashboardViewModel.listUsers.observe(this) { setUserData(it.toMutableList()) }
        dashboardViewModel.isLoading.observe(this) { showLoading(it) }
        setUserData(dashboardViewModel.parseJSON(this.assets.open("githubuser.json")))
    }

    private fun setUserData(userlist: MutableList<User>) {
        val adapter = UserAdapter(this,this)
        adapter.users = userlist
        binding.recyclerView.adapter = adapter
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
                dashboardViewModel.getUsersFromAPi(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })

        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                setUserData(mutableListOf())
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                setUserData(dashboardViewModel.parseJSON(this@DashboardActivity.assets.open("githubuser.json")))
                return true
            }
        })

        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarLayout.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onItemClicked(user: User) {
        val toUserDetails = Intent(this@DashboardActivity, DetailUserActivity::class.java)
        toUserDetails.putExtra(EXTRA_USER, user)
        startActivity(toUserDetails)
    }
}