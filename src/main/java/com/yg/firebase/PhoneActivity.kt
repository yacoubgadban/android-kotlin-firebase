package com.yg.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yg.firebase.databinding.ActivityPhoneBinding
import java.util.concurrent.TimeUnit

class PhoneActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPhoneBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var myCallbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    var verificationCode=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPhoneBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth=FirebaseAuth.getInstance()


        binding.btnSendSmsCode.setOnClickListener {

            var number=binding.etPhoneNumber.text.toString()
            number="+972$number"
            sendVerificationCode(number)

        }


        binding.btnSubmitCode.setOnClickListener {

            signInWithSmsCode()

        }

        myCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Toast.makeText(applicationContext,"code has been sent to you",Toast.LENGTH_LONG).show()
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(applicationContext,p0.message,Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)

                verificationCode=p0
            }
        }
    }

    private fun signInWithSmsCode(){

        val userEnterCode = binding.etCode.text.toString()

        val credential = PhoneAuthProvider.getCredential(verificationCode , userEnterCode)

        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential){

        auth.signInWithCredential(credential).addOnCompleteListener {
            task ->
            if(task.isSuccessful){

                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()

            }else{
                Toast.makeText(this,task.exception.toString(),Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(myCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }
}