package com.yg.firebase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ValueEventListener
import com.yg.firebase.databinding.UserItemBinding


class UserAdapter( var context: Context,  var userList: ArrayList<User>):RecyclerView.Adapter<UserAdapter.UsersViewHolder>() {

    inner class UsersViewHolder( val adapterBinding : UserItemBinding ) : RecyclerView.ViewHolder(adapterBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding =UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UsersViewHolder(binding)
    }



    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.adapterBinding.tvName.text=userList[position].name
        holder.adapterBinding.tvAge.text=userList[position].age.toString()
        holder.adapterBinding.tvEmail.text=userList[position].email

        holder.adapterBinding.linearLayout.setOnClickListener {
            val intent=Intent(context,UpdateUserActivity::class.java)
            intent.putExtra("id",userList[position].id)
            intent.putExtra("name",userList[position].name)
            intent.putExtra("age",userList[position].age)
            intent.putExtra("email",userList[position].email)

            context.startActivity(intent)

        }

    }
    override fun getItemCount(): Int {
        return userList.size
    }

    fun getUserId(position: Int):String{
        return userList[position].id.toString()
    }

    fun getUserName(position: Int):String{
        return userList[position].name.toString()
    }


}