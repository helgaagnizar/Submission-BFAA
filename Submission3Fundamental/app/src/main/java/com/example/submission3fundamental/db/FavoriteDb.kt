package com.example.submission3fundamental.db

import android.content.Context
import androidx.room.*
import com.example.submission3fundamental.model.User

@Database(entities = [User::class], version = 1)
abstract class FavoriteDb:RoomDatabase() {

    abstract fun favoriteDao():FavoriteDAO

    companion object{
        @Volatile
        private var INSTANCE :FavoriteDb?=null

        fun getDb(context: Context):FavoriteDb{
            val  tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDb::class.java,
                    "test"
                ).build()
                INSTANCE =instance
                return  instance
            }
        }

    }
}