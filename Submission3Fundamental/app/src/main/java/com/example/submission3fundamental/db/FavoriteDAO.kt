package com.example.submission3fundamental.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.submission3fundamental.model.User


@Dao
interface FavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun tambahFavo(user: User)

    @Query("SELECT * FROM favorite_user ORDER BY id ASC")
    fun bacaFavo(): LiveData<List<User>>

    @Query("SELECT * FROM favorite_user ORDER BY id ASC")
    fun bacaSemuaFavo(): Cursor

    @Update
    suspend fun updateFavo(user: User)

    @Delete
    suspend fun hapusFavo(user: User)

    @Query("DELETE FROM favorite_user")
    suspend fun hapusSemua()

    @Query("SELECT * FROM favorite_user WHERE favorite_user.username = :username")
    suspend fun checkById(username: String): User

}

