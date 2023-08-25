package com.yg.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yg.firebase.databinding.ActivityCreateUserBinding

class CreateUserActivity : AppCompatActivity() {

    //binding
    private lateinit var binding: ActivityCreateUserBinding

    //database
    private val database : FirebaseDatabase=FirebaseDatabase.getInstance()
    private val reference:DatabaseReference =database.reference.child("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCreateUserBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        //back to home page button
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        //create user button
        binding.btnCreate.setOnClickListener {
            addUserFunction()
        }

    }

    private fun addUserFunction(){
        val id:String?=reference.push().key.toString()
        val name:String?=binding.etName.text.toString()
        val age:Int?=binding.etAge.text.toString().toInt()
        val email:String?=binding.etEmail.text.toString()

        val user=User(id,name,age, email)
        reference.child(id!!).setValue(user).addOnCompleteListener {task->
            if(task.isSuccessful){
                showToast("New user has been added to db successfully")
                finish()
            }else{
                showToast(task.exception.toString())
            }
        }
    }

    private fun showToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}