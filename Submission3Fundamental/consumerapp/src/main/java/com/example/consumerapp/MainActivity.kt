package com.example.consumerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var mFavoriteViewModel: FavoriteViewModel
    private lateinit var adapter: UserAdapter
//    private lateinit var favoriteViewModel: FavoriteViewModel

    companion object {
        private const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "FAVORITE User Github"

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        mFavoriteViewModel = ViewModelProvider(
            this,
        ).get(FavoriteViewModel::class.java)

        showRecyclerList()
    }

    private fun showRecyclerList() {
        binding.lvUsers.layoutManager = LinearLayoutManager(this)
        binding.lvUsers.adapter = adapter

        mFavoriteViewModel.setFavorite(this)

        mFavoriteViewModel.getFavorite()?.observe(this, { data ->
            if (data != null){
                adapter.setData(data)
            }
        })
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
            }
        })
    }
}