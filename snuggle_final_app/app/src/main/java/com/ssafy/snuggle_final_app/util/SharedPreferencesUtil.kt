package com.ssafy.snuggle_final_app.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.ssafy.snuggle_final_app.dto.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SharedPreferencesUtil(context: Context) {
    val SHARED_PREFERENCES_NAME = "smartstore_preference"
    val COOKIES_KEY_NAME = "cookies"

    private val LAST_BEACON_SCAN_TIME_KEY = "last_beacon_scan_time"

    var preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    //사용자 정보 저장
    fun addUser(user: User) {
        val editor = preferences.edit()
        editor.putString("id", user.userId)
        editor.putString("name", user.nickname)
        editor.apply()
    }

    fun getUser(): User {
        val id = preferences.getString("id", "")
        val name = preferences.getString("name", "")

        // id와 name이 빈 문자열이 아닌 경우에만 User 객체를 반환
        return if (!id.isNullOrEmpty() && !name.isNullOrEmpty()) {
            User(userId = id, password = "", nickname = name,  age = 0, gender = "", path = "", token = "", img = "", stamps = 0, stampList = ArrayList())
        } else {
            User()  // 기본값을 가진 User 객체 반환
        }
    }


    fun deleteUser() {
        //preference 지우기
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    fun addUserCookie(cookies: HashSet<String>) {
        val editor = preferences.edit()
        editor.putStringSet(COOKIES_KEY_NAME, cookies)
        editor.apply()
    }

    fun getUserCookie(): MutableSet<String>? {
        return preferences.getStringSet(COOKIES_KEY_NAME, HashSet())
    }

    fun deleteUserCookie() {
        preferences.edit().remove(COOKIES_KEY_NAME).apply()
    }

    fun addNotice(info: String) {
        val list = getNotice()

        list.add(info)
        val json = Gson().toJson(list)

        preferences.edit().let {
            it.putString("notice", json)
            it.apply()
        }
    }

    fun setNotice(list: MutableList<String>) {
        preferences.edit().let {
            it.putString("notice", Gson().toJson(list))
            it.apply()
        }
    }

    fun getNotice(): MutableList<String> {
        val str = preferences.getString("notice", "")!!
        val list = if (str.isEmpty()) mutableListOf<String>() else Gson().fromJson(
            str,
            MutableList::class.java
        ) as MutableList<String>

        return list
    }

    /*** Dialog 24시간에 1회만 띄우기 위한 코드 ***/
//    private val SHARED_PREFERENCES_NAME = "smartstore_preference"
    private val LAST_EVENT_DATE_KEY = "last_event_date"
//    private val preferences: SharedPreferences =
//        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun saveCurrentDate() {
        val editor = preferences.edit()
        val currentDate = getCurrentDate() // 오늘 날짜를 "yyyy-MM-dd" 형식으로 얻음
        editor.putString(LAST_EVENT_DATE_KEY, currentDate)
        editor.apply()
    }

    // 저장된 날짜가 오늘인지 확인하는 메서드
    fun isTodayEventDate(): Boolean {
        val savedDate = preferences.getString(LAST_EVENT_DATE_KEY, "")
        val currentDate = getCurrentDate()
        return savedDate == currentDate
    }

    // 현재 날짜를 "yyyy-MM-dd" 형식의 문자열로 반환하는 메서드
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }

}