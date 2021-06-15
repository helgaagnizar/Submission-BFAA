package com.example.submission3fundamental.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3fundamental.activity.DetailUser
import com.example.submission3fundamental.model.User
import com.example.submission3fundamental.adapter.UserAdapter
import com.example.submission3fundamental.databinding.FragmentFollowingBinding
import com.example.submission3fundamental.viewModel.MainViewModel

class FollowingFragment : Fragment() {
    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        binding.fgFollowing.setHasFixedSize(true)
        showRecyclerList()
        showLoading(true)
    }

    private fun showRecyclerList() {
        binding.fgFollowing.layoutManager = LinearLayoutManager(requireContext())
        binding.fgFollowing.adapter = adapter

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        mainViewModel.setFollowing(username)
        mainViewModel.getFollowing().observe(requireActivity(), { data ->
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
        FollowersFragment.username = data.username.toString()
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