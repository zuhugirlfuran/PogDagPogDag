package com.ssafy.snuggle_final_app.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ssafy.snuggle_final_app.LoginActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var loginActivity: LoginActivity

    // 바인딩 객체 선언 및 초기화
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginActivity = context as LoginActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        // 고양이 gif 파일 넣기
        Glide.with(this).load(R.raw.jumping_cat).override(560, 560).into(binding.loginCatImageView)

        // 메인 화면으로 이동
        binding.loginBtnSignIn.setOnClickListener {
            loginActivity.openFragment(1)
        }

        // 회원가입 화면으로 이동
        binding.loginBtnSignUp.setOnClickListener {
            loginActivity.openFragment(2)
        }

        return binding.root
    }

}