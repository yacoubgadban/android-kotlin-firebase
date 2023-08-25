package com.yg.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yg.firebase.databinding.ActivityAppLoginBinding

class AppLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth=Firebase.auth
        super.onCreate(savedInstanceState)
        binding=ActivityAppLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.btnLogin.setOnClickListener {

            val userEmail=binding.etLoginEmail.text.toString()
            val userPassword=binding.etLoginPassword.text.toString()

            signInWithFirebase(userEmail,userPassword)

        }

        binding.btnLoginSignup.setOnClickListener {
            val intent = Intent(this , SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnForget.setOnClickListener {
            val intent = Intent(this,ForgetActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLoginWithPhoneNumber.setOnClickListener {
            val intent = Intent(this,PhoneActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signInWithFirebase(userEmail:String,userPassword:String){

        auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener {
            task ->
            if(task.isSuccessful){

                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()

            }else{
                Toast.makeText(this,"Invalid email or password",Toast.LENGTH_SHORT).show()
            }
        }

    }
    override fun onStart(){
        super.onStart()
        val user=auth.currentUser

        if(user != null){
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}