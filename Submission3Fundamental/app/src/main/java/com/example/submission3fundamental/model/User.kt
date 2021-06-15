package com.example.submission3fundamental.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite_user")
data class User (
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,
        var username: String?="",
        var name: String?="",
        var location: String?="",
        var repository: String?="",
        var company: String?="",
        var followers: String?="",
        var following: String?="",
        var avatar: String?="",
        ) : Parcelable