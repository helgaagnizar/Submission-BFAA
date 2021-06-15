package com.example.submission2fundamental

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
        var username: String?="",
        var name: String?="",
        var location: String?="",
        var repository: String?="",
        var company: String?="",
        var followers: String?="",
        var following: String?="",
        var avatar: String?="",
        var html_url : String?=""
        ) : Parcelable