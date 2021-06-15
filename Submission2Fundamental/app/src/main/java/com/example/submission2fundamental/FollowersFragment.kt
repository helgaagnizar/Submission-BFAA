package com.example.submission2fundamental

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2fundamental.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {

    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentFollowersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        binding.fgFollowers.setHasFixedSize(true)
        showRecyclerList()
        showLoading(true)
    }

    private fun showRecyclerList() {
        binding.fgFollowers.layoutManager = LinearLayoutManager(requireContext())
        binding.fgFollowers.adapter = adapter

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        mainViewModel.setFollowers(username)
        mainViewModel.getFollowers().observe(this, { data ->
            if (data != null) {
                adapter.setData(data)
                showLoading(false)
            }
        })
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showDetail(data)
            }
        })
    }

    private fun showDetail(data: User) {
        username = data.username.toString()
        FollowingFragment.username = data.username.toString()

        val intentDetail = Intent(requireContext(), DetailUser::class.java)
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

    companion object {
        var username = "username"
    }
}