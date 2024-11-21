package com.ssafy.snuggle_final_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ssafy.snuggle_final_app.databinding.ActivityLoginBinding
import com.ssafy.snuggle_final_app.login.JoinFragment
import com.ssafy.snuggle_final_app.login.LoginFragment

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 초기 프래그먼트 설정 (MainFragment)
        replaceFragment(LoginFragment())

    }

    private fun replaceFragment(loginFragment: LoginFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout_login, loginFragment)
            .commit()
    }

    fun openFragment(int: Int){
        val transaction = supportFragmentManager.beginTransaction()
        when(int){
            // 1번은 뒤로 가기 누르면 로그인 화면으로 오지 않도록
            // 2번은 뒤로 가기 누르면 로그인 화면으로 오도록.
            1 -> {
                val intent = Intent(this, MainActivity::class.java).apply{
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
            }
            // fragment에서 뒤로가기를 대응하려면, addToBackStack을 활용한다.
            2 -> transaction.replace(R.id.frame_layout_login, JoinFragment())
                .addToBackStack(null)

            3 -> {
                // 회원가입한 뒤 돌아오면, 2번에서 addToBackStack해 놓은게 남아 있어서,
                // stack을 날려 줘야 한다. stack날리기.
                supportFragmentManager.popBackStack()
                transaction.replace(R.id.frame_layout_login, LoginFragment())
            }
        }

        // 즉시 반영이 아님.
        transaction.commit()
    }

}