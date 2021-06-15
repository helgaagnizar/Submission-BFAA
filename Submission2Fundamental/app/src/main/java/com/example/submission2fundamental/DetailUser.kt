package com.example.submission2fundamental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2fundamental.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUser : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityDetailUserBinding

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(DetailUser.TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        var username = intent.getStringExtra(EXTRA_PERSON)
        setDataUserDetail(username!!)
    }

    private fun setDataUserDetail(username: String) {
        mainViewModel.setUserDetail(username)
        mainViewModel.getUserDetail().observe(this, { data ->
            if (data != null) {
                with(binding) {
                    Glide.with(this@DetailUser)
                        .load(data.avatar)
                        .apply(RequestOptions().override(250, 250))
                        .into(detailAvatar)

                    supportActionBar?.title = username

                    detailUsername.text = data.username as String
                    detailName.text = data.name as String
                    detailLocation.text = data.location as String
                    detailRepository.text = data.repository as String
                    detailCompany.text = data.company as String
                    detailFollowers.text = data.followers as String
                    detailFollowing.text = data.following as String
                    showLoading(false)
                }
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_PERSON = "extra_person"
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}