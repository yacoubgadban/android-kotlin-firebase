package com.yg.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yg.firebase.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivitySignupBinding.inflate(layoutInflater)
        auth=Firebase.auth
        super.onCreate(savedInstanceState)
        val view=binding.root
        setContentView(view)

        binding.btnSignupLogin.setOnClickListener {
            val intent = Intent(this,AppLoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignup.setOnClickListener {
            val userEmail=binding.etSignupEmail.text.toString()
            val userPassword=binding.etSignupPassword.text.toString()

            signupWithFirebase(userEmail,userPassword)
        }
    }

    private fun signupWithFirebase(userEmail:String, userPassword:String){
        auth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener {
            task->
            if (task.isSuccessful){
                Toast.makeText(this,"Your account has been created",Toast.LENGTH_LONG).show()
                finish()
            }else{
                Toast.makeText(this,task.exception.toString(),Toast.LENGTH_LONG).show()
            }
        }
    }
}