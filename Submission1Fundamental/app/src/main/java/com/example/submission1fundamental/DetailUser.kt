package com.example.submission1fundamental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetailUser : AppCompatActivity() {

    companion object {
        const val EXTRA_PERSON = "extra_person"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        val tvImgPhoto: ImageView = findViewById(R.id.detail_avatar)
        val tvUsername: TextView = findViewById(R.id.detail_username)
        val tvName: TextView = findViewById(R.id.detail_name)
        val tvLocation: TextView = findViewById(R.id.detail_location)
        val tvRepository: TextView = findViewById(R.id.detail_repository)
        val tvCompany: TextView = findViewById(R.id.detail_company)
        val tvFollowers: TextView = findViewById(R.id.detail_followers)
        val tvFollowing: TextView = findViewById(R.id.detail_following)

        val user = intent.getParcelableExtra<User>(EXTRA_PERSON) as User

        tvImgPhoto.setImageResource(user.avatar)
        tvUsername.text = user.username
        tvName.text = user.name
        tvLocation.text = user.location
        tvRepository.text = user.repository.toString()
        tvCompany.text = user.company
        tvFollowers.text = user.followers.toString()
        tvFollowing.text = user.following.toString()
    }
}