package com.example.submission3fundamental.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3fundamental.adapter.UserAdapter
import com.example.submission3fundamental.databinding.ActivityFavoriteBinding
import com.example.submission3fundamental.fragment.FollowersFragment
import com.example.submission3fundamental.fragment.FollowingFragment
import com.example.submission3fundamental.model.User
import com.example.submission3fundamental.viewModel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var mFavoriteViewModel: FavoriteViewModel
    private lateinit var adapter: UserAdapter

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "User Favorite Github"

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        showLoading(false)

        mFavoriteViewModel = ViewModelProvider(
            this
        ).get(FavoriteViewModel::class.java)

        showRecyclerList()
    }

    private fun showRecyclerList() {
        binding.lvUsers.layoutManager = LinearLayoutManager(this)
        binding.lvUsers.adapter = adapter

        mFavoriteViewModel.readAllData.observe(this, { userItems ->
            if (userItems != null) {
                adapter.setDataFav(userItems)
            }
        })
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showDetail(data)
            }
        })
    }

    private fun showDetail(data: User) {
        FollowingFragment.username = data.username.toString()
        FollowersFragment.username = data.username.toString()

        val intentDetail = Intent(this, DetailUser::class.java)
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
}