package com.example.chaat_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private  lateinit var userRecyclerView: RecyclerView
    private  lateinit var userList: ArrayList<User>
    private  lateinit var adapter: UserAdapter
    private lateinit var  mAuth: FirebaseAuth
    private lateinit var mDbref: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth= FirebaseAuth.getInstance()
        mDbref=FirebaseDatabase.getInstance().getReference()

        Toast.makeText(this@MainActivity,"Tumhari yaad mei!!",Toast.LENGTH_LONG).show()

        userList = ArrayList()
        adapter = UserAdapter(this,userList)
        userRecyclerView=findViewById(R.id.userRecycler)
        userRecyclerView.layoutManager=LinearLayoutManager(this)
        userRecyclerView.adapter=adapter

        mDbref.child("user").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //TODO("Not yet implemented")
                userList.clear()
                for (postSnapshot in snapshot.children){
                    val currentUser= postSnapshot.getValue(User::class.java)
                    if (mAuth.currentUser?.uid !=currentUser?.uid)
                    userList.add(currentUser!!)
                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menue,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==R.id.logout_button){
            mAuth.signOut()
            val intent = Intent(this@MainActivity,login::class.java)
            finish()
            startActivity(intent)

        }
        return super.onOptionsItemSelected(item)
    }

}