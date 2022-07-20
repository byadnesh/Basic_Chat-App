package com.example.chaat_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chaat_app.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private  lateinit var binding: ActivitySignupBinding
    private lateinit var mDbref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)//setContentView(R.layout.activity_login)
        setContentView(binding.root)
        mAuth=FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener{
            val name = binding.edtName.text.toString()
            val email =binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()


            signUp(name,email,password)
        }
    }
    private fun signUp(name:String,email:String,password:String){
        //logic to create new users
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    //code for jump to home
                    addUserToDatabase(name,email, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@signup,login::class.java)
                    finish()
                    startActivity(intent)
                }else{
                    Toast.makeText(this@signup,"Some error occured ",Toast.LENGTH_SHORT).show()
                }

                }
    }

    private fun addUserToDatabase(name: String,email: String, uid: String){
        mDbref= FirebaseDatabase.getInstance().getReference()
        mDbref.child("user").child(uid).setValue(User(name,email, uid))

    }


}


