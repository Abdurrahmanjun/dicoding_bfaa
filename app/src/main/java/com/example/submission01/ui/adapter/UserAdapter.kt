package com.example.submission01.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.submission01.R
import com.example.submission01.databinding.ItemUserBinding
import com.example.submission01.domain.model.User

class UserAdapter internal  constructor(private val context: Context) : BaseAdapter() {

    internal var users = mutableListOf<User>()

    override fun getCount(): Int = users.size
    override fun getItem(i: Int): Any = users[i]
    override fun getItemId(i: Int): Long = i.toLong()

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        var itemView = view
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_user, viewGroup, false)
        }

        val viewHolder = ViewHolder(itemView as View)
        val hero = getItem(position) as User
        viewHolder.bind(hero)
        return itemView
    }

    private inner class ViewHolder(view: View) {
        private val binding = ItemUserBinding.bind(view)

        fun bind(user: User) {
            val fileName: String = user.avatar.replace("@drawable/", "")
            val avatarId = context.resources.getIdentifier(fileName, "drawable", context.packageName)

            binding.imgPhoto.setImageResource(avatarId)
            binding.txtUsername.text = user.username
            binding.txtName.text = user.name
            binding.txtLocation.text = user.location
        }
    }

}