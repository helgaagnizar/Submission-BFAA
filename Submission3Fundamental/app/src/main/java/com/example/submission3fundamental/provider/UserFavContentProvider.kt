package com.example.submission3fundamental.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.submission3fundamental.db.FavoriteDAO
import com.example.submission3fundamental.db.FavoriteDb

class UserFavContentProvider : ContentProvider() {

    companion object {
        const val AUTHOR = "com.example.submission1fundamental"
        const val TABLE_NAME = "favorite_user"
        const val ID_USER_FAV = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHOR, TABLE_NAME, ID_USER_FAV)
        }
    }

    private lateinit var favoriteDAO: FavoriteDAO

    override fun onCreate(): Boolean {
        favoriteDAO = context?.let {
                data -> FavoriteDb.getDb(data)?.favoriteDao()
        }!!
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var cursor: Cursor?
        when (uriMatcher.match(uri)) {
            ID_USER_FAV -> {
                cursor = favoriteDAO.bacaSemuaFavo()
                if (context != null) {
                    cursor.setNotificationUri(context?.contentResolver, uri)
                }
            }
            else -> {
                cursor = null
            }
        }
        return cursor
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }


    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}