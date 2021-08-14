package com.corsuevisionplus.tamazuj.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.corsuevisionplus.tamazuj.R
import com.corsuevisionplus.tamazuj.common.Common
import com.corsuevisionplus.tamazuj.models.Chat
import java.text.SimpleDateFormat
import java.util.*


class ChatAdapter(private val context: Context, arrayList: List<Chat>?)
    :RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    val MSG_TYPE_LEFT = 0
    val MSG_TYPE_RIGHT = 1
    private var sharedPreferences:SharedPreferences? = null
    private val arrayList: List<Chat>?
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ChatViewHolder {
            if (viewType == MSG_TYPE_RIGHT){
                val v: View = LayoutInflater.from(context).inflate(R.layout.chat_item_right_sent, parent, false)
                return ChatViewHolder(v)
            }else{
                val v: View = LayoutInflater.from(context).inflate(R.layout.chat_item_left_received, parent, false)
                return ChatViewHolder(v)
            }

    }

    override fun onBindViewHolder(holder: ChatAdapter.ChatViewHolder, position: Int) {
        holder.showMessage.text = arrayList?.get(position)?.message
        holder.timeMessage.text = arrayList?.get(position)?.time
        convertTime(arrayList?.get(position)?.time!!)

    }
    fun convertTime(time: String): String? {
        val formatter = SimpleDateFormat("h:mm a")
        return formatter.format(Date(time.toLong()))
    }
    override fun getItemCount(): Int {
        return arrayList!!.size

    }
    inner class ChatViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        var showMessage: TextView
        var timeMessage: TextView

        init {
            showMessage = itemView.findViewById(R.id.tv_chat_received)
            timeMessage = itemView.findViewById(R.id.tv_chat_time_received)
        }
    }

    init {
        this.arrayList = arrayList
    }

    override fun getItemViewType(position: Int): Int {
        sharedPreferences = context.getSharedPreferences("SETTING_PREF", AppCompatActivity.MODE_PRIVATE)
        if (arrayList?.get(position)?.receiver!!.equals(sharedPreferences!!.getString("ID",null))){
            return MSG_TYPE_RIGHT

        }else{
           return MSG_TYPE_LEFT
     }

    }

}