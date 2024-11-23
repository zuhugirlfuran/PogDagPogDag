package com.ssafy.snuggle_final_app.ui.chatbot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.databinding.FragmentChatBotBinding

class ChatBotFragment : Fragment() {

    private var _binding: FragmentChatBotBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ChatBotAdapter
    private val messages: MutableList<ChatMessage> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide AppBar and BottomNavigation
        val mainActivity = activity as? MainActivity
        mainActivity?.let {
            it.findViewById<View>(R.id.app_bar)?.visibility = View.GONE
            it.findViewById<View>(R.id.bottomNavigation)?.visibility = View.GONE
        }

        // Set up RecyclerView
        adapter = ChatBotAdapter(messages)
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewChat.adapter = adapter

        // Send message when button is clicked
        binding.buttonSend.setOnClickListener {
            val messageText = binding.editTextChat.text.toString().trim()
            if (messageText.isNotEmpty()) {
                // Add user message
                messages.add(ChatMessage(messageText, isUser = true))
                // Add chatbot reply (dummy for now)
                messages.add(ChatMessage("안녕하세요! 어떻게 도와드릴까요?", isUser = false))

                // Update RecyclerView
                adapter.notifyDataSetChanged()
                binding.recyclerViewChat.scrollToPosition(messages.size - 1)
                binding.editTextChat.text.clear()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Show AppBar and BottomNavigation when fragment is destroyed
        val mainActivity = activity as? MainActivity
        mainActivity?.let {
            it.findViewById<View>(R.id.app_bar)?.visibility = View.VISIBLE
            it.findViewById<View>(R.id.bottomNavigation)?.visibility = View.VISIBLE
        }
        _binding = null
    }
}
