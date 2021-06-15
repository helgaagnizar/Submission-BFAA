package com.example.consumerapp

import android.database.Cursor

object MappingHelper {
    fun mapCursor(cursor: Cursor?): ArrayList<User> {
        val list = ArrayList<User>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ID))
                val username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
                val avatar = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR))
                list.add(
                    User(
                        id,
                        username,
                        avatar)
                )
            }
        }
        return list
    }
}