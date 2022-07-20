package com.example.chaat_app

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chaat_app.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private  lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)//setContentView(R.layout.activity_login)
        setContentView(binding.root)
        mAuth=FirebaseAuth.getInstance()

        supportActionBar?.hide()
        Toast.makeText(this@login, "Application By Yadnesh💻", Toast.LENGTH_LONG).show()

        binding.signupButton.setOnClickListener{
            val intent = Intent(this,signup::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener{
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()


            Login(email,password)
        }

    }

    private fun Login(email: String,password:String){
        //login in
        mAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){task->
                if (task.isSuccessful){
                    val intent =Intent(this@login,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                }else{
                    Toast.makeText(this@login,"User Does Not Exist",Toast.LENGTH_SHORT).show()
                }
            }
    }
}