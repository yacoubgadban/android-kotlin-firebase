package com.yg.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yg.firebase.databinding.ActivityForgetBinding

class ForgetActivity : AppCompatActivity() {
    private lateinit var binding:ActivityForgetBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth=Firebase.auth
        binding=ActivityForgetBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        binding.btnForgetSend.setOnClickListener {
            val email=binding.etForgetEmail.text.toString()
            auth.sendPasswordResetEmail(email).addOnCompleteListener {
                task->
                if(task.isSuccessful){
                    Toast.makeText(this,"We send a password email reset to your email",Toast.LENGTH_LONG).show()
                    val intent= Intent(this,AppLoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,task.exception?.toString(),Toast.LENGTH_LONG).show()
                    val intent= Intent(this,AppLoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}