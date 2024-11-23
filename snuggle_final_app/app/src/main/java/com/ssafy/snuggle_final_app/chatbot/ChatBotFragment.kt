package com.ssafy.snuggle_final_app.chatbot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.databinding.FragmentChatBotBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ChatBotFragment : Fragment() {

    private var _binding: FragmentChatBotBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ChatBotAdapter
    private val messages: MutableList<ChatMessage> = mutableListOf()

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS) // 연결 타임아웃 30초
        .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)    // 읽기 타임아웃 30초
        .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)   // 쓰기 타임아웃 30초
        .build()

    private val apiKey =
        "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"

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
                addToChat(messageText, ChatMessage.SENT_BY_ME)
                // Call OpenAI API
                callAPI(messageText)
                // Clear input field
                binding.editTextChat.text.clear()
            }
        }
    }

    private fun addToChat(message: String, sentBy: String) {
        val currentTime = getCurrentTime()
        messages.add(ChatMessage(message, sentBy, currentTime))
        adapter.notifyDataSetChanged()
        binding.recyclerViewChat.scrollToPosition(messages.size - 1)
    }

    private fun callAPI(question: String) {
        // Add a loading indicator
        val loadingMessage = ChatMessage("...", ChatMessage.SENT_BY_BOT, null)
        messages.add(loadingMessage)
        adapter.notifyItemInserted(messages.size - 1)
        binding.recyclerViewChat.scrollToPosition(messages.size - 1)

        val jsonObject = JSONObject()
        try {
            jsonObject.put("model", "gpt-4")
            val messagesArray = JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "system")
                    put("content", "너는 한국어로만 답변할 수 있어. 우리 어플 이름은 폭닥폭닥이고, 털실과 관련된 제품 및 diy 키트를 판매하고 있어. 고객들에게 친절한 상담 부탁해.")
                })
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", question)
                })
            }
            jsonObject.put("messages", messagesArray)
            jsonObject.put("max_tokens", 1000)
            jsonObject.put("temperature", 0)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaType(),
            jsonObject.toString()
        )
        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .header("Authorization", "Bearer $apiKey")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (isAdded) { // Fragment가 활성화 상태인지 확인
                    requireActivity().runOnUiThread {
                        // Remove loading indicator
                        messages.remove(loadingMessage)
                        addResponse("Failed to load response: ${e.message}")
                    }
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful) {
                    try {
                        val jsonObject = JSONObject(responseBody ?: "{}")
                        val choices = jsonObject.optJSONArray("choices")
                        if (choices != null && choices.length() > 0) {
                            val result = choices.getJSONObject(0)
                                .optJSONObject("message")
                                ?.optString("content", "No response")?.trim()

                            if (isAdded) { // Fragment가 활성화 상태인지 확인
                                requireActivity().runOnUiThread {
                                    // Remove loading indicator
                                    messages.remove(loadingMessage)
                                    addResponse(result ?: "No response")
                                }
                            }
                        } else {
                            if (isAdded) {
                                requireActivity().runOnUiThread {
                                    // Remove loading indicator
                                    messages.remove(loadingMessage)
                                    addResponse("No response from API")
                                }
                            }
                        }
                    } catch (e: JSONException) {
                        if (isAdded) {
                            requireActivity().runOnUiThread {
                                messages.remove(loadingMessage)
                                addResponse("Failed to parse response")
                            }
                        }
                        e.printStackTrace()
                    }
                } else {
                    if (isAdded) {
                        requireActivity().runOnUiThread {
                            messages.remove(loadingMessage)
                            addResponse("Error: ${response.code}\nMessage: ${response.message}\nBody: $responseBody")
                        }
                    }
                }
            }
        })
    }

    private fun addResponse(response: String) {
        addToChat(response, ChatMessage.SENT_BY_BOT)
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val mainActivity = activity as? MainActivity
        mainActivity?.let {
            it.findViewById<View>(R.id.app_bar)?.visibility = View.VISIBLE
            it.findViewById<View>(R.id.bottomNavigation)?.visibility = View.VISIBLE
        }

        // Fragment를 벗어나면 메시지 삭제
        messages.clear()
        _binding = null
    }
}
