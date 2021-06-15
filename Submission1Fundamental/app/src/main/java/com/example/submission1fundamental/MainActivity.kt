package com.example.submission1fundamental

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var dataUsername: Array<String>
    private lateinit var dataName: Array<String>
    private lateinit var dataRepository: Array<String>
    private lateinit var dataFollowers: Array<String>
    private lateinit var dataFollowing: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataCompany: Array<String>
    private lateinit var dataPhoto: TypedArray
    private lateinit var rvUsers: RecyclerView
    private var list = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvUsers = findViewById(R.id.lv_users)
        rvUsers.setHasFixedSize(true)

        prepare()
        addItem()
        showRecyclerList()
    }

    private fun showRecyclerList() {
        rvUsers.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = UserAdapter(list)
        rvUsers.adapter = listUserAdapter

        listUserAdapter.setOnClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showDetailUser(data)
            }
        })
    }

    private fun prepare() {
        dataUsername = resources.getStringArray(R.array.username)
        dataName = resources.getStringArray(R.array.name)
        dataRepository = resources.getStringArray(R.array.repository)
        dataFollowers = resources.getStringArray(R.array.followers)
        dataPhoto = resources.obtainTypedArray(R.array.avatar)
        dataFollowing = resources.getStringArray(R.array.following)
        dataLocation = resources.getStringArray(R.array.location)
        dataCompany = resources.getStringArray(R.array.company)
    }

    private fun addItem() {
        for (position in dataUsername.indices) {
            val user = User(
                dataUsername[position],
                dataName[position],
                dataLocation[position],
                dataRepository[position].toInt(),
                dataCompany[position],
                dataFollowers[position].toInt(),
                dataFollowing[position].toInt(),
                dataPhoto.getResourceId(position, -1)
            )
            list.add(user)
        }
    }

    private fun showDetailUser(data: User) {
        val user = User(

            data.username,
            data.name,
            data.location,
            data.repository,
            data.company,
            data.followers,
            data.following,
            data.avatar
        )
        val intentDetail = Intent(this@MainActivity, DetailUser::class.java)
        intentDetail.putExtra(DetailUser.EXTRA_PERSON, data)
        startActivity(intentDetail)
    }
}