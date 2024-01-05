package com.example.recyleviewexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyleviewexample.databinding.UserDataItemBinding

class UserDataAdapter(private val users: List<User>, private val clickListener: (User) -> Unit) :
    RecyclerView.Adapter<UserDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserDataItemBinding.inflate(inflater)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class ViewHolder(private val binding: UserDataItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.username.text = user.username
            binding.address.text = user.address
            binding.phoneNumber.text = user.phoneNumber
            binding.emailAddress.text = user.emailAddress

            binding.root.setOnClickListener { clickListener(user) }
        }
    }
}