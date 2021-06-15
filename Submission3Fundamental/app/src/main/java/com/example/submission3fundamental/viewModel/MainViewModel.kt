package com.example.submission3fundamental.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission3fundamental.model.User
import com.example.submission3fundamental.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()
    val detailUser = MutableLiveData<User>()
    private lateinit var binding: ActivityMainBinding

    fun setUser() {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")

        val listItems = ArrayList<User>()

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONArray(result)
                    Log.d(TAG, result)
                    for (i in 0 until responseObject.length()) {
                        val item = responseObject.getJSONObject(i)
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")

                        val user = User(
                            username = username,
                            avatar = avatar
                        )
                        listItems.add(user)
                    }
                    listUsers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("OnFailure", error.message.toString())
            }
        })
    }

    fun getUser(): LiveData<ArrayList<User>> {
        return listUsers
    }

    fun setUserDetail(username: String) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username"
        client.addHeader("Authorization", "")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val user = User()
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    Log.d(TAG, result)

                    user.username = responseObject.getString("login")
                    user.name = ": " + responseObject.getString("name")
                    user.location = ": " + responseObject.getString("location")
                    user.repository = ": " + responseObject.getString("public_repos")
                    user.company = ": " + responseObject.getString("company")
                    user.followers = responseObject.getString("followers")
                    user.following = responseObject.getString("following")
                    user.avatar = responseObject.getString("avatar_url")

                    detailUser.postValue(user)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("OnFailure", error.message.toString())
            }
        })
    }

    fun getUserDetail(): LiveData<User> {
        return detailUser
    }

    fun setDataForSearch(username: String) {
        val listItems = ArrayList<User>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$username"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObject = JSONObject(result)
                    val count = responseObject.getInt("total_count")
                    if (count >= 1) {
                        val items = responseObject.getJSONArray("items")
                        for (i in 0 until items.length()) {
                            val item = items.getJSONObject(i)
                            val user = User()
                            user.username = item.getString("login")
                            user.avatar = item.getString("avatar_url")
                            listItems.add(user)
                        }
                        listUsers.postValue(listItems)
                    } else {
                        listUsers.postValue(listItems)
                    }
                } catch (e: Exception) {
                    Log.d(TAG, e.message.toString())
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getSearchUser(): LiveData<ArrayList<User>> {
        return listUsers
    }

    fun setFollowers(username: String) {
        val listItems = ArrayList<User>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/followers"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    Log.d(TAG, result)
                    val responseArray = JSONArray(result)
                    if (responseArray.length() > 0) {
                        for (i in 0 until responseArray.length()) {
                            val items = responseArray.getJSONObject(i)
                            val username = items.getString("login")
                            val avatar = items.getString("avatar_url")
                            val user = User(
                                username = username,
                                avatar = avatar
                            )
                            listItems.add(user)
                        }
                        listUsers.postValue(listItems)
                    } else {
                        listUsers.postValue(listItems)
                    }
                } catch (e: Exception) {
                    Log.d(TAG, e.message.toString())
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error?.message.toString())
            }

        })
    }

    fun getFollowers(): LiveData<ArrayList<User>> {
        return listUsers
    }

    fun setFollowing(username: String) {
        val listItems = ArrayList<User>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/following"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    Log.d(TAG, result)
                    val responseArray = JSONArray(result)
                    if (responseArray.length() > 0) {
                        for (i in 0 until responseArray.length()) {
                            val items = responseArray.getJSONObject(i)
                            val username = items.getString("login")
                            val avatar = items.getString("avatar_url")
                            val user = User(
                                username = username,
                                avatar = avatar
                            )
                            listItems.add(user)
                        }
                        listUsers.postValue(listItems)
                    } else {
                        listUsers.postValue(listItems)
                    }
                } catch (e: Exception) {
                    Log.d(TAG, e.message.toString())
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error?.message.toString())
            }

        })
    }

    fun getFollowing(): LiveData<ArrayList<User>> {
        return listUsers
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

//    fun addFav(id: Int, avatar: String, username: String) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val user = com.example.submission1fundamental.DatabaseContract(
//                USERNAME,
//                NAME,
//                AVATAR,
//                LOCATION,
//                COMPANY,
//                FOLLOWERS,
//                FOLLOWING,
//                FAVORITE
//
//            )
//
//            LOCATION = "location"
//            const val REPOSITORY = "repository"
//            const val COMPANY = "company"
//            const val FOLLOWERS = "followers"
//            const val FOLLOWING = "following"
//            const val FAVORITE = "favorite"
//        }
//    }

}