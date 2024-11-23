package com.ssafy.snuggle_final_app.scanner

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
    private lateinit var videoSrc: String
    private lateinit var videoTitle: String
    private lateinit var videoContent: String
    private var videoLike: Int = 0

    // 북마크 상태를 저장하는 변수
    private var bookmarkClicked = false

    companion object {
        fun newInstance(videoSrc: String, videoTitle: String, videoContent: String, videoLike: Int): ScannerVideoFragment {
            val fragment = ScannerVideoFragment()
            val args = Bundle()
            args.putString("videoSrc", videoSrc)
            args.putString("videoTitle", videoTitle)
            args.putString("videoContent", videoContent)
            args.putInt("videoLike", videoLike)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            videoSrc = it.getString("videoSrc") ?: ""
            videoTitle = it.getString("videoTitle") ?: ""
            videoContent = it.getString("videoContent") ?: ""
            videoLike = it.getInt("videoLike", 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScannerVideoBinding.inflate(inflater, container, false)

        //== 비디오 설정 ==//
        // MediaController를 VideoView에 연결
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.videoView)

        val uri = Uri.parse(videoSrc)
        
        //url 설정
        binding.videoView.setMediaController(mediaController)
        binding.videoView.setVideoURI(uri)
        binding.videoView.requestFocus()
        //title 설정
        binding.scannerTvTitle.text = videoTitle
        //설명 설정
        binding.scannerTvDetail.text = videoContent
        //bookmark 설정
        binding.scannerTvBookmark.text = videoLike.toString()

        // 비디오 재생 시작
        binding.videoView.start()

        
        //== 북마크 설정 ==//

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

    private fun updateBookmarkIcon() {
        if (bookmarkClicked) {
            binding.scannerIvBookmark.setImageResource(R.drawable.scanner_bookmark_icon_cliked)
        } else {
            binding.scannerIvBookmark.setImageResource(R.drawable.scanner_bookmark_icon)
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)?.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)?.visibility =
            View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
