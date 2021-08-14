package com.corsuevisionplus.tamazuj.activities




import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.corsuevisionplus.tamazuj.adapters.ChatAdapter
import com.corsuevisionplus.tamazuj.common.Common
import com.corsuevisionplus.tamazuj.databinding.ActivityChatBinding
import com.corsuevisionplus.tamazuj.models.Chat
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ChatActivity : AppCompatActivity() {
    private lateinit var chatBinding: ActivityChatBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mchat: List<Chat>
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatBinding = ActivityChatBinding.inflate(layoutInflater)
        val view = chatBinding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences("SETTING_PREF", MODE_PRIVATE)

        val lm = LinearLayoutManager(this)
        chatBinding.recyclerViewMessagesRecord.layoutManager = lm
        userID = sharedPreferences.getString("ID", null).toString()
        val common = Common(this)
        chatBinding.sendMessage.setOnClickListener {
            val message = chatBinding.textMessage.text.toString()
            val time = System.currentTimeMillis().toString()
            if (message.equals("")){
               sendMessage(common.getUser()!!.id.toString(), userID, message, time)
            }else{
                Toast.makeText(this@ChatActivity, "You can't send empty message", Toast.LENGTH_SHORT).show()
            }
                chatBinding.textMessage.setText("")
        }
        val postReference:DatabaseReference = FirebaseDatabase.getInstance().getReference("UserInfo").child(userID)
        val postListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                chatBinding.appBarChat.tvProfileUserName.setText(common.getUser()!!.name)
                readMessage(common.getUser()!!.id.toString(), userID)

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        postReference.addValueEventListener(postListener)
    }


    private fun sendMessage(sender: String, receiver: String, message: String, time: String){
        val reference:DatabaseReference = FirebaseDatabase.getInstance().reference
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["sender"] = sender
        hashMap["receiver"] = receiver
        hashMap["message"] = message
        hashMap["time"] = time
        reference.child("Chat").push().setValue(hashMap)

    }
    private fun readMessage(myid: String, userid: String){
        mchat = ArrayList()
        val postReference:DatabaseReference = FirebaseDatabase.getInstance().getReference("Chat")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                (mchat as ArrayList<Chat>).clear()
                for (snapshot in dataSnapshot.children){
                    val chat = snapshot.getValue(Chat::class.java)
                    if (chat!!.receiver == myid && chat.sender == userid ||
                            chat.receiver == userid && chat.sender == myid ){

                        (mchat as ArrayList<Chat>).add(chat)

                    }
                    chatAdapter = ChatAdapter(this@ChatActivity, mchat)
                    chatBinding.recyclerViewMessagesRecord.adapter = chatAdapter
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        postReference.addValueEventListener(postListener)

    }


        private fun currentUser(userid: String){
            val edit = sharedPreferences.edit()
            edit.putString("CURRENTUSER", userid)
            edit.apply()
        }


    override fun onResume() {
        super.onResume()
        currentUser(userID)
    }

    override fun onPause() {
        super.onPause()
        currentUser("none")
    }

}


