package com.example.submission3fundamental.repo

import androidx.lifecycle.LiveData
import com.example.submission3fundamental.model.User
import com.example.submission3fundamental.db.FavoriteDAO

class FavoriteRepository(private val favoriteDAO: FavoriteDAO) {

    val readAll: LiveData<List<User>> = favoriteDAO.bacaFavo()

    suspend fun tambahFavorite(user: User) {
        favoriteDAO.tambahFavo(user)
    }

    suspend fun updateFavorite(user: User) {
        favoriteDAO.updateFavo(user)
    }

    suspend fun hapusFavorite(user: User) {
        favoriteDAO.hapusFavo(user)
    }

    suspend fun hapusSemua() {
        favoriteDAO.hapusSemua()
    }

    suspend fun checkById(username: String) = favoriteDAO.checkById(username)
}