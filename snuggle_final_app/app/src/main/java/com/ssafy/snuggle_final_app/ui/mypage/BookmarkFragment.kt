package com.ssafy.snuggle_final_app.mypage

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.databinding.FragmentBookmarkBinding
import com.ssafy.snuggle_final_app.ui.mypage.BookmarkAdapter
import com.ssafy.snuggle_final_app.ui.mypage.BookmarkViewModel
import com.ssafy.snuggle_final_app.ui.scanner.ScannerVideoFragment

class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(
    FragmentBookmarkBinding::bind,
    R.layout.fragment_bookmark
) {

    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private lateinit var adapter: BookmarkAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BookmarkAdapter(requireContext(), mutableListOf())
        binding.bookmarkLv.adapter = adapter

        // 사용자 ID 가져오기
        val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId

        // ViewModel에서 데이터 요청
        bookmarkViewModel.getBookmarkList(userId)
        // ViewModel 데이터 관찰
        bookmarkViewModel.bookmarkList.observe(viewLifecycleOwner) { bookmarks ->
            adapter.updateData(bookmarks)
        }

        binding.bookmarkLv.setOnItemClickListener{parent, view, position, id ->
            val selectedItem = adapter.getItem(position)

            val fragment = ScannerVideoFragment.newInstance( // 선택된 북마크 정보 받아오기
                videoSrc = selectedItem.videoSrc,
                videoTitle = selectedItem.videoTitle,
                videoContent = selectedItem.videoContent,
                videoLike = selectedItem.videoLike,
                taggingId = selectedItem.taggingId
            )
            
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frameLayout, fragment)
                .addToBackStack(null)
                .commit()
        }

    }

    override fun onResume() {
        super.onResume()
        // Activity의 BottomNavigationView를 숨김
        activity?.findViewById<ConstraintLayout>(R.id.bottom_navigation)?.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        // Activity의 BottomNavigationView를 다시 보임
        activity?.findViewById<ConstraintLayout>(R.id.bottom_navigation)?.visibility =
            View.VISIBLE
    }
}