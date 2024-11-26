package com.ssafy.snuggle_final_app.ui.login

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import androidx.fragment.app.viewModels
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.LoginActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.databinding.FragmentLoginBinding

private const val TAG = "LoginFragment"
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::bind,
    R.layout.fragment_login
)
{
    private lateinit var loginActivity: LoginActivity

    private val viewModel: LoginFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginActivity = context as LoginActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 고양이 gif 파일 넣기
        Glide.with(this).load(R.raw.jumping_cat).override(560, 560).into(binding.loginCatImageView)

        // 회원가입 화면으로 이동
        binding.loginBtnSignUp.setOnClickListener {
            loginActivity.openFragment(2)
        }

        /* ===== 회원가입 ===== */
        moveToSignup()

        /* ===== 비밀번호 표시 ===== */
        binding.loginCheckPw.setOnClickListener {
            // 현재 상태에 따라 비밀번호 표시/숨기기
            if (binding.loginCheckPw.tag == "visible") {
                // 현재 보이는 상태 -> 감추기
                binding.loginEtPw.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.loginCheckPw.setImageResource(R.drawable.login_pw_uncheck) // 숨김 상태 아이콘으로 변경
                binding.loginCheckPw.tag = "hidden"
            } else {
                // 현재 숨김 상태 -> 보이기
                binding.loginEtPw.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.loginCheckPw.setImageResource(R.drawable.login_pw_check) // 표시 상태 아이콘으로 변경
                binding.loginCheckPw.tag = "visible"
            }

            // 커서 위치 유지
            binding.loginEtPw.setSelection(binding.loginEtPw.text.length)
        }

        // 로그인 쿠키 남기기
        registerObserver()
        /* ===== 로그인 ===== */
        binding.loginBtnSignIn.setOnClickListener {
            val id = binding.loginEtId.text.toString()
            val password = binding.loginEtPw.text.toString()
            viewModel.login(id, password)
        }
    }

    private fun moveToSignup() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user.userId.isNotEmpty()) {
                showToast("로그인 성공: ${user.userId}")
                (requireActivity() as LoginActivity).openFragment(1)
            } else {
                showToast("로그인 실패: 아이디 또는 비밀번호를 확인하세요.")
            }
        }
    }

    private fun registerObserver() {

        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user.userId.isEmpty()) {  // 아이디 비어있음 => 로그인 실패
                showToast("id 혹은 password를 확인해주세요.")
            } else {  //  로그인 성공
                // sharedpreference에 기록
                ApplicationClass.sharedPreferencesUtil.addUser(user)
                Log.d(TAG, "registerObserver: Id ${user.userId}")
                Log.d(TAG, "registerObserver: Name ${user.nickname}")
                showToast("로그인 되었습니다.")
                loginActivity.openFragment(1)  // 로그인 성공시, main으로 이동
            }
        }
    }


}