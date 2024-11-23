package com.ssafy.snuggle_final_app.ui.login

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.LoginActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.User
import com.ssafy.snuggle_final_app.databinding.FragmentJoinBinding

private const val TAG = "JoinFragment_싸피"

class JoinFragment : BaseFragment<FragmentJoinBinding>(
    FragmentJoinBinding::bind,
    R.layout.fragment_join
)
{
    private lateinit var loginActivity: LoginActivity

    private var isUsedId = false  // 사용중인 id인지
    private var clicked = false  // id 중복 체크 버튼

    private val viewModel: LoginFragmentViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginActivity = context as LoginActivity
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.joinCheckId.setImageResource(R.drawable.join_checkbox_id_blank)

        // ID 중복 확인 버튼
        binding.joinCheckId.setOnClickListener {
            val inputId = binding.joinEtId.text.toString()
            clicked = true

            if (inputId.isEmpty()) {
                binding.joinCheckId.setImageResource(R.drawable.join_checkbox_id_wrong)
                clicked = false
                showToast("ID를 입력해주세요.")
                return@setOnClickListener
            }

            // id 중복 요청
            viewModel.checkAvailableId(inputId)
        }

        viewModel.isAvailableId.observe(viewLifecycleOwner) { isAvailable ->
            isUsedId = isAvailable
            if (isAvailable) {
                binding.joinCheckId.setImageResource(R.drawable.join_checkbox_id)
                showToast("사용 가능한 ID입니다.")
            } else {
                binding.joinCheckId.setImageResource(R.drawable.join_checkbox_id_wrong)
                showToast("사용할 수 없는 ID입니다.")
            }
        }

        // 회원 가입 버튼 => 메인 화면으로 이동
        binding.joinBtnSignUp.setOnClickListener {

            val id = binding.joinEtId.text.toString()
            val password = binding.joinEtPw.text.toString()
            val nickname = binding.joinEtNickname.text.toString()
            val ageString = binding.joinEtAge.text.toString()
            val gender = binding.joinEtGender.tag.toString() // 'M' 또는 'F' 값을 가져옴
            val path = binding.joinEtPath.text.toString()

            // 숫자 변환 시 예외 처리를 위한 try-catch
            val age: Int? = try {
                ageString.toInt()
            } catch (e: NumberFormatException) {
                0
            }

            if (!isUsedId || !clicked) {
                showToast("ID를 체크해 주세요.")
                binding.joinCheckId.setImageResource(R.drawable.join_checkbox_id_wrong)
                return@setOnClickListener
            } else if (password.isEmpty() || id.isEmpty() || nickname.isEmpty() || ageString.isEmpty() || gender.isEmpty() || path.isEmpty()) {
                Toast.makeText(requireContext(), "빈 칸을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (clicked) {
                binding.joinCheckId.setImageResource(R.drawable.join_checkbox_id)
            }

            // User 객체 생성
            val user = User(id, password, nickname, age!!, gender, path, "", "", 0)
            // ViewModel을 통해 회원가입 요청
            viewModel.join(user)


        }

        // LiveData 관찰
        viewModel.joinStatus.observe(viewLifecycleOwner) { isInserted ->
            if (isInserted) {
                showToast("회원가입이 완료되었습니다.")
                loginActivity.openFragment(1) // 메인 화면 이동
            } else {
                showToast("회원가입에 실패했습니다.")
                loginActivity.openFragment(3)  // 로그인 화면으로 이동
            }
        }


        /* ===== 비밀번호 표시 ===== */
        binding.joinCheckPw.setOnClickListener {
            // 현재 상태에 따라 비밀번호 표시/숨기기
            if (binding.joinCheckPw.tag == "visible") {
                // 현재 보이는 상태 -> 감추기
                binding.joinEtPw.inputType =
                    android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.joinCheckPw.setImageResource(R.drawable.login_pw_uncheck) // 숨김 상태 아이콘으로 변경
                binding.joinCheckPw.tag = "hidden"
            } else {
                // 현재 숨김 상태 -> 보이기
                binding.joinEtPw.inputType =
                    android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.joinCheckPw.setImageResource(R.drawable.login_pw_check) // 표시 상태 아이콘으로 변경
                binding.joinCheckPw.tag = "visible"
            }

            // 커서 위치 유지
            binding.joinEtPw.setSelection(binding.joinEtPw.text.length)
        }


        /* ===== 커스텀 성별 다이얼로그 ===== */
        // 커스텀 성별 다이얼로그 설정 부분
        binding.joinEtGender.setOnClickListener {
            showCustomGenderDialog()
        }

        /* ===== 커스텀 가입경로 다이얼로그 ===== */
        binding.joinEtPath.setOnClickListener {
            showJoinPathDialog()
        }

    }

    private fun showJoinPathDialog() {
        val pathDialogView = layoutInflater.inflate(R.layout.join_dialog_path, null)

        // 다이얼로그 내 각 뷰를 찾아서 처리
        val radioInsta: ImageView = pathDialogView.findViewById(R.id.radioInsta)
        val radioYoutube: ImageView = pathDialogView.findViewById(R.id.radioYoutube)
        val radioBlog: ImageView = pathDialogView.findViewById(R.id.radioBlog)
        val radioSsafy: ImageView = pathDialogView.findViewById(R.id.radioSsafy)
        val linearInsta: LinearLayout = pathDialogView.findViewById(R.id.linearInsta)
        val linearYoutube: LinearLayout = pathDialogView.findViewById(R.id.linearYoutube)
        val linearBlog: LinearLayout = pathDialogView.findViewById(R.id.linearBlog)
        val linearSsafy: LinearLayout = pathDialogView.findViewById(R.id.linearSsafy)

        // 선택된 값을 저장할 변수 초기화
        var selectedPath: String? = null

        // AlertDialog.Builder를 사용하여 다이얼로그 생성
        val pathDialog = AlertDialog.Builder(requireContext())
            .setView(pathDialogView)
            .setPositiveButton("확인") { _, _ ->
                // 확인 버튼 클릭 시 선택된 값 설정
                selectedPath?.let {
                    binding.joinEtPath.setText(it)
                }
            }
            .create()

        pathDialog.setOnShowListener {
            val positiveButton = pathDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(requireContext().getColor(R.color.black)) // 검은색으로 변경
        }

        // 다이얼로그의 배경을 둥근 테두리로 설정
        pathDialog.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded_border)

        // 가입경로 선택 시 아이콘 변경 및 상태 관리
        linearInsta.setOnClickListener {
            radioInsta.setImageResource(R.drawable.join_checkbox_id)
            radioInsta.tag = "selected"
            radioYoutube.setImageResource(R.drawable.dialog_checkbox_blank)
            radioYoutube.tag = null
            radioBlog.setImageResource(R.drawable.dialog_checkbox_blank)
            radioBlog.tag = null
            radioSsafy.setImageResource(R.drawable.dialog_checkbox_blank)
            radioSsafy.tag = null
            selectedPath = "인스타그램"
        }

        linearYoutube.setOnClickListener {
            radioYoutube.setImageResource(R.drawable.join_checkbox_id)
            radioYoutube.tag = "selected"
            radioInsta.setImageResource(R.drawable.dialog_checkbox_blank)
            radioInsta.tag = null
            radioBlog.setImageResource(R.drawable.dialog_checkbox_blank)
            radioBlog.tag = null
            radioSsafy.setImageResource(R.drawable.dialog_checkbox_blank)
            radioSsafy.tag = null
            selectedPath = "유튜브"
        }

        linearBlog.setOnClickListener {
            radioBlog.setImageResource(R.drawable.join_checkbox_id)
            radioBlog.tag = "selected"
            radioInsta.setImageResource(R.drawable.dialog_checkbox_blank)
            radioInsta.tag = null
            radioYoutube.setImageResource(R.drawable.dialog_checkbox_blank)
            radioYoutube.tag = null
            radioSsafy.setImageResource(R.drawable.dialog_checkbox_blank)
            radioSsafy.tag = null
            selectedPath = "블로그"
        }

        linearSsafy.setOnClickListener {
            radioSsafy.setImageResource(R.drawable.join_checkbox_id)
            radioSsafy.tag = "selected"
            radioInsta.setImageResource(R.drawable.dialog_checkbox_blank)
            radioInsta.tag = null
            radioYoutube.setImageResource(R.drawable.dialog_checkbox_blank)
            radioYoutube.tag = null
            radioBlog.setImageResource(R.drawable.dialog_checkbox_blank)
            radioBlog.tag = null
            selectedPath = "SSAFY"
        }

        pathDialog.show() // 다이얼로그 표시
    }

    private fun showCustomGenderDialog() {
        // 커스텀 다이얼로그 레이아웃을 인플레이트
        val dialogView = layoutInflater.inflate(R.layout.join_dialog_gender, null)

        // 다이얼로그 내 각 뷰를 찾아서 처리
        val radioMale: ImageView = dialogView.findViewById(R.id.radioMale)
        val radioFemale: ImageView = dialogView.findViewById(R.id.radioFemale)
        val linearMale: LinearLayout = dialogView.findViewById(R.id.linearMale)
        val linearFemale: LinearLayout = dialogView.findViewById(R.id.linearFemale)

        // AlertDialog.Builder를 사용하여 다이얼로그를 생성
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView) // 커스텀 레이아웃을 적용
            .setPositiveButton("확인") { _, _ ->
                // 선택된 성별을 EditText에 설정
                when {
                    radioMale.tag == "selected" -> {
                        binding.joinEtGender.setText("남성")
                        binding.joinEtGender.tag = "M" // 'M' 값 설정
                    }

                    radioFemale.tag == "selected" -> {
                        binding.joinEtGender.setText("여성")
                        binding.joinEtGender.tag = "F" // 'F' 값 설정
                    }
                }
            }
            .create()

        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(requireContext().getColor(R.color.black)) // 검은색으로 변경
        }

        // 다이얼로그의 배경을 둥근 테두리로 설정
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded_border)

        // 성별 선택 시 아이콘 변경 및 상태 관리
        linearMale.setOnClickListener {
            radioMale.setImageResource(R.drawable.join_checkbox_id)
            radioMale.tag = "selected"
            radioFemale.setImageResource(R.drawable.dialog_checkbox_blank)
            radioFemale.tag = null
        }

        linearFemale.setOnClickListener {
            radioFemale.setImageResource(R.drawable.join_checkbox_id)
            radioFemale.tag = "selected"
            radioMale.setImageResource(R.drawable.dialog_checkbox_blank)
            radioMale.tag = null
        }

        dialog.show() // 다이얼로그 표시
    }

}
