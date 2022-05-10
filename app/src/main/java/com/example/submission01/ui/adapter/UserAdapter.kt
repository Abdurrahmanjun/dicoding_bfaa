package com.example.submission01.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission01.R
import com.example.submission01.databinding.ItemUserBinding
import com.example.submission01.domain.model.User

class UserAdapter internal constructor(private val context: Context,private val listener: OnItemClickListener) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    internal var users = mutableListOf<User>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (users[position].id == 0) {
            val fileName: String = users[position].avatar.replace("@drawable/", "")
            val avatarId =
                context.resources.getIdentifier(fileName, "drawable", context.packageName)
            viewHolder.binding.imgPhoto.setImageResource(avatarId)
        }else {
            Glide.with(context).load(users[position].avatar).into(viewHolder.binding.imgPhoto)
        }

        viewHolder.binding.txtUsername.text = users[position].username
        viewHolder.binding.txtName.text = users[position].name
        viewHolder.binding.txtLocation.text = users[position].location

        viewHolder.binding.itemLayout.setOnClickListener {
            listener?.onItemClicked(users[position])
        }
    }

    override fun getItemCount() = users.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemUserBinding.bind(view)
    }

    interface OnItemClickListener{
        fun onItemClicked(user: User)
    }
}