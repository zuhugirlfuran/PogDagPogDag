package com.ssafy.snuggle_final_app.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* ===== 비밀번호 표시 ===== */
        binding.loginCheckPw.setOnClickListener {
            // 현재 상태에 따라 비밀번호 표시/숨기기
            if (binding.loginCheckPw.tag == "visible") {
                // 현재 보이는 상태 -> 감추기
                binding.loginEtPw.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.loginCheckPw.setImageResource(R.drawable.login_pw_uncheck) // 숨김 상태 아이콘으로 변경
                binding.loginCheckPw.tag = "hidden"
            } else {
                // 현재 숨김 상태 -> 보이기
                binding.loginEtPw.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.loginCheckPw.setImageResource(R.drawable.login_pw_check) // 표시 상태 아이콘으로 변경
                binding.loginCheckPw.tag = "visible"
            }

            // 커서 위치 유지
            binding.loginEtPw.setSelection(binding.loginEtPw.text.length)
        }
    }

}