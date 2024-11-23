package com.ssafy.snuggle_final_app.chatbot

data class ChatMessage(
    val message: String,
    val sentBy: String,
    val time: String?
) {
    companion object {
        const val SENT_BY_ME = "me"
        const val SENT_BY_BOT = "bot"
    }
}
