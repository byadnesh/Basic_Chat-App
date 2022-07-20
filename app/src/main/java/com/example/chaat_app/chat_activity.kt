package com.example.chaat_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class chat_activity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messagebox: EditText
    private lateinit var sentButton: ImageView
    private lateinit var messageAdapter: message_adapter
    private lateinit var messageList: ArrayList<com.example.chaat_app.Message>
    private lateinit var mDbref : DatabaseReference


    var receiverRoom: String? =null
    var senderRoom: String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val name =  intent.getStringExtra("name")
        val receiverUid =  intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        mDbref= FirebaseDatabase.getInstance().getReference()

        senderRoom=receiverUid+senderUid
        receiverRoom=senderUid+receiverUid

        supportActionBar?.title=name



        chatRecyclerView=findViewById(R.id.charRecycler)
        messagebox=findViewById(R.id.messageBox)
        sentButton=findViewById(R.id.sendbtn)
        messageList=ArrayList()
        messageAdapter = message_adapter(this, messageList)

        chatRecyclerView.layoutManager=LinearLayoutManager(this)
        chatRecyclerView.adapter=messageAdapter

        //logic for adding data ro recycler view
        mDbref.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                   for(postSnapshot in snapshot.children){
                       val messageessage =postSnapshot.getValue(com.example.chaat_app.Message::class.java)
                       messageList.add(messageessage!!)
                   }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

//adding message to databasee
        sentButton.setOnClickListener{
            val message = messagebox.text.toString()
            val messageObject= Message(message,senderUid)

            mDbref.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbref.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messagebox.setText("")


        }

    }
}