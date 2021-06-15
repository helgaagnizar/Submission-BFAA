package com.example.submission1fundamental

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.StringBuilder
import java.util.ArrayList

class UserAdapter (private val listUser: ArrayList<User>): RecyclerView.Adapter<UserAdapter.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var tvFollowers: TextView = itemView.findViewById(R.id.tv_followers)
        var tvRepository: TextView = itemView.findViewById(R.id.tv_repository)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_user_avatar)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]

        holder.imgPhoto.setImageResource(user.avatar)
        holder.tvUsername.text = StringBuilder("@${user.username}")
        holder.tvName.text =user.name
        holder.tvRepository.text = StringBuilder("Repository : ${user.repository.toString()}")
        holder.tvFollowers.text = StringBuilder("Followers : ${user.followers.toString()}")
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}