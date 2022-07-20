package com.example.chaat_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class message_adapter(val context: Context, val messageList: ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val Item_recieve=1;
    val item_sent=2;


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        if(viewType==1){
            //infalte receive
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return RecieveViewHolder(view)
        }else{
            //inflate send
            val view: View =LayoutInflater.from(context).inflate(R.layout.receiver,parent,false)
            return SentViewHolder(view)
        }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if(holder.javaClass==SentViewHolder::class.java){
            val viewHolder= holder as SentViewHolder
            holder.sentMessage.text=currentMessage.message
        }else {
            val viewHolder = holder as RecieveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage =messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.sendId)){
            return  Item_recieve
        }else{
           return item_sent
        }
    }



    override fun getItemCount(): Int {
        return messageList.size

    }

    class SentViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val sentMessage =itemView.findViewById<TextView>(R.id.sent_message)

    }
    class RecieveViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val receiveMessage =itemView.findViewById<TextView>(R.id.receive_message)

    }
}