package com.yg.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yg.firebase.databinding.ActivityUpdateUserBinding

class UpdateUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateUserBinding

    private  val database: FirebaseDatabase=FirebaseDatabase.getInstance()
    private  val reference: DatabaseReference=database.reference.child("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUpdateUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnBack.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

       getData()
       binding.btnUpdate.setOnClickListener {
           updateData()
           val intent=Intent(this,MainActivity::class.java)
           startActivity(intent)
           finish()
       }
    }

    private fun getData(){
        val name = intent.getStringExtra("name")
        val age = intent.getIntExtra("age",0).toString()
        val email=intent.getStringExtra("email")

        binding.etUpdateName.setText(name)
        binding.etUpdateAge.setText(age)
        binding.etUpdateEmail.setText(email)
    }

    private fun updateData(){

        val id=intent.getStringExtra("id").toString()
        val name=binding.etUpdateName.text.toString()
        val age=binding.etUpdateAge.text.toString().toInt()
        val email =binding.etUpdateEmail.text.toString()

        val userMap= mutableMapOf<String,Any>()
        //id in userMap come from the model in user class
        userMap["id"]=id
        userMap["name"]=name
        userMap["age"]=age
        userMap["email"]=email

        reference.child(id).updateChildren(userMap).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this,"user has been updated successfully",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,task.exception.toString(),Toast.LENGTH_SHORT).show()

            }
        }





    }
}