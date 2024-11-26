package com.ssafy.snuggle_final_app.ui.chatbot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.snuggle_final_app.chatbot.ChatMessage
import com.ssafy.snuggle_final_app.databinding.ItemChatChatbotBinding
import com.ssafy.snuggle_final_app.databinding.ItemChatUserBinding

class ChatBotAdapter(private val messages: List<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_USER = 0
        private const val VIEW_TYPE_CHATBOT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].sentBy == ChatMessage.SENT_BY_ME) VIEW_TYPE_USER else VIEW_TYPE_CHATBOT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_USER -> {
                val binding = ItemChatUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                UserViewHolder(binding)
            }
            else -> {
                val binding = ItemChatChatbotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ChatBotViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder) {
            is UserViewHolder -> holder.bind(message)
            is ChatBotViewHolder -> holder.bind(message)
        }
    }

    override fun getItemCount(): Int = messages.size

    inner class UserViewHolder(private val binding: ItemChatUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessage) {
            binding.textMessageUser.text = message.message
            binding.textTime.text = message.time ?: ""
        }
    }

    inner class ChatBotViewHolder(private val binding: ItemChatChatbotBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessage) {
            binding.textMessageBot.text = message.message
            binding.textTime.text = message.time ?: ""
        }
    }
}
