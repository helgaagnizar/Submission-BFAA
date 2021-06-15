package com.example.submission3fundamental.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3fundamental.R
import com.example.submission3fundamental.adapter.UserAdapter
import com.example.submission3fundamental.databinding.ActivityMainBinding
import com.example.submission3fundamental.fragment.FollowersFragment
import com.example.submission3fundamental.fragment.FollowingFragment
import com.example.submission3fundamental.model.User
import com.example.submission3fundamental.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var listData: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lvUsers.setHasFixedSize(true)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainViewModel.setUser()
        mainViewModel.getUser().observe(this, { User ->
            if (User != null) {
                adapter.setData(User)
                showLoading(false)
            }
        })
        showRecyclerList()
        showLoading(true)

        showRecyclerListSearch()
        binding.search.apply {
            setOnSearchClickListener {
                this.onActionViewExpanded()
                search()
            }
            setOnClickListener {
                this.onActionViewExpanded()
                search()
            }
        }
    }

    private fun showRecyclerList() {
        binding.lvUsers.layoutManager = LinearLayoutManager(this)
        binding.lvUsers.adapter = adapter

        mainViewModel.setUser()
        mainViewModel.getUser().observe(this, { data ->
            if (data != null) {
                adapter.setData(data)
                showLoading(false)
            }
        })
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showDetailUser(data)
            }
        })
    }

    private fun showDetailUser(data: User) {
        FollowersFragment.username = data.username.toString()
        FollowingFragment.username = data.username.toString()

        val intentDetail = Intent(this@MainActivity, DetailUser::class.java)
        intentDetail.putExtra(DetailUser.EXTRA_PERSON, data.username)
        startActivity(intentDetail)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun search() {
        binding.search.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (query.isNotEmpty()) {
                        listData.clear()
                        mainViewModel.setDataForSearch(query)
                        showLoading(true)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isNotEmpty()) {
                        mainViewModel.setDataForSearch(newText)
                        showLoading(true)
                    } else {
                        showRecyclerList()
                        showLoading(true)
                    }
                    return true
                }
            })
        }
    }

    private fun showRecyclerListSearch() {
        binding.lvUsers.layoutManager = LinearLayoutManager(this)
        binding.lvUsers.adapter = adapter

        mainViewModel.setUser()
        mainViewModel.getSearchUser().observe(this, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        })
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showDetailUser(data)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_fav -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_setting -> {
                val intent = Intent(this, SettingAlarmActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}