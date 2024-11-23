package com.ssafy.snuggle_final_app.ui.scanner

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.databinding.FragmentScannerVideoBinding

class ScannerVideoFragment : Fragment() {
    private var _binding: FragmentScannerVideoBinding? = null
    private val binding get() = _binding!!

    // 북마크 상태를 저장하는 변수
    private var bookmarkClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScannerVideoBinding.inflate(inflater, container, false)

        // 테스트용 비디오 URL 설정
        val videoUrl = "https://www.w3schools.com/html/mov_bbb.mp4"

        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.videoView)

        val uri = Uri.parse(videoUrl)
        binding.videoView.setMediaController(mediaController)
        binding.videoView.setVideoURI(uri)
        binding.videoView.requestFocus()

        binding.videoView.start()

        // 북마크 초기 상태 설정
        updateBookmarkIcon()

        // 북마크 클릭 이벤트 리스너 설정
        binding.scannerLlBookmark.setOnClickListener {
            bookmarkClicked = !bookmarkClicked // 상태 토글
            updateBookmarkIcon()
            // TODO: DB에서 북마크 수 증가 + 내 북마크 리스트에 추가

            if (bookmarkClicked) {
                // TODO: DB에서 북마크 수 증가 + 내 북마크 리스트에 추가
            } else {
                // TODO: DB에서 북마크 수 감소 + 내 북마크 리스트에서 제거
            }
        }

        return binding.root
    }

    // 북마크 상태에 따라 아이콘 변경
    private fun updateBookmarkIcon() {
        if (bookmarkClicked) {
            binding.scannerIvBookmark.setImageResource(R.drawable.scanner_bookmark_icon_cliked)
        } else {
            binding.scannerIvBookmark.setImageResource(R.drawable.scanner_bookmark_icon)
        }
    }

    override fun onResume() {
        super.onResume()
        // Activity의 BottomNavigationView를 숨김
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)?.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        // Activity의 BottomNavigationView를 다시 보임
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)?.visibility =
            View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
