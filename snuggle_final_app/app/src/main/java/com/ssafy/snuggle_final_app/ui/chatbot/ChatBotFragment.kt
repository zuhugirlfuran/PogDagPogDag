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

    // OpenAI 유료 API KEY.
    // 유료 버전이 아니면 안됨
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

        // 여기에 채팅들이 주르륵
        adapter = ChatBotAdapter(messages)
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewChat.adapter = adapter

        // 보내기 버튼 누르면 채팅 진행
        binding.buttonSend.setOnClickListener {
            val messageText = binding.editTextChat.text.toString().trim()
            if (messageText.isNotEmpty()) {
                // User의 메시지
                addToChat(messageText, ChatMessage.SENT_BY_ME)
                // OpenAI API 호춯
                callAPI(messageText)
                // TextView 입력값 지우기
                binding.editTextChat.text.clear()
            }
        }
    }

    // 어댑터를 통해 recyclerview에 채팅 기록 추가
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
                // 이건 프롬프트로 보내는 설정
                put(JSONObject().apply {
                    put("role", "system")
                    put("content", "너는 한국어로만 답변할 수 있어. 우리 어플 이름은 폭닥폭닥이고, 털실과 관련된 제품 및 diy 키트를 판매하고 있어. 고객들에게 친절한 상담 부탁해.")
                })
                // 이걸 사용자가 보내는 메시지
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", question)
                })
            }
            jsonObject.put("messages", messagesArray)
            // 챗봇이 답변하는 값의 길이.
            // 너무 길어지면 답변하는 시간이 오래 걸려 챗봇이 터질 수 있음
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
            // 이 주소로 메시지를 보내야 함
            .url("https://api.openai.com/v1/chat/completions")
            // 이건 필수 header값으로 넘겨야 하는 거
            .header("Authorization", "Bearer $apiKey")
            // json 형식의 message를 넘김
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (isAdded) { // Fragment가 활성화 상태인지 확인
                    requireActivity().runOnUiThread {
                        // 로딩되는 동안 떴던 "..." 삭제
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
                                    messages.remove(loadingMessage)
                                    addResponse(result ?: "No response")
                                }
                            }
                        } else {
                            if (isAdded) {
                                requireActivity().runOnUiThread {
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

    // 챗봇의 채팅도 recyclerview에 추가
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
