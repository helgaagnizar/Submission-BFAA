package com.example.consumerapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var listFavorite = MutableLiveData<ArrayList<User>>()

    fun setFavorite(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseContract.FavoriteColumns.CONTENT_URI, null, null, null, null
        )
        val listConverted = MappingHelper.mapCursor(cursor)
        listFavorite.postValue(listConverted)
    }

    fun getFavorite(): LiveData<ArrayList<User>> {
        return listFavorite
    }

}
