package com.example.consumerapp

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
        var avatar: String?="",
        ) : Parcelable