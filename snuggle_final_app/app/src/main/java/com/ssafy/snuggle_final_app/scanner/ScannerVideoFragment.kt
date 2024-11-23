package com.ssafy.snuggle_final_app.scanner

import ScannerViewModel
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.local.SharedPreferencesUtil
import com.ssafy.snuggle_final_app.databinding.FragmentScannerVideoBinding

class ScannerVideoFragment : Fragment() {
    private var _binding: FragmentScannerVideoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScannerViewModel by viewModels()

    private lateinit var videoSrc: String
    private lateinit var videoTitle: String
    private lateinit var videoContent: String
    private lateinit var taggingId: String
    private var videoLike: Int = 0

    companion object {
        fun newInstance(
            videoSrc: String,
            videoTitle: String,
            videoContent: String,
            videoLike: Int,
            taggingId: String
        ): ScannerVideoFragment {
            val fragment = ScannerVideoFragment()
            val args = Bundle().apply {
                putString("videoSrc", videoSrc)
                putString("videoTitle", videoTitle)
                putString("videoContent", videoContent)
                putInt("videoLike", videoLike)
                putString("taggingId", taggingId)
            }
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
            taggingId = it.getString("taggingId") ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScannerVideoBinding.inflate(inflater, container, false)

        //== Video 설정 ==//
        setupVideoPlayer()

        //== 북마크 설정 ==//
        // 북마크 상태 초기화
        val userId = SharedPreferencesUtil(requireContext()).getUser().userId
        if (userId.isNotEmpty()) {
            viewModel.initializeBookmarkState(userId, taggingId)
        } else {
            Log.e("BOOKMARK", "사용자 ID가 비어있습니다.")
        }

        // LiveData 관찰
        setupObservers()

        // 북마크 클릭 이벤트 리스너
        binding.scannerLlBookmark.setOnClickListener {
            if (userId.isNotEmpty()) {
                viewModel.toggleBookmark(userId, taggingId)
            } else {
                Log.e("BOOKMARK", "사용자 ID가 비어있습니다.")
            }
        }

        return binding.root
    }

    private fun setupVideoPlayer() {
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.videoView)

        binding.videoView.apply {
            setMediaController(mediaController)
            setVideoURI(Uri.parse(videoSrc))
            requestFocus()
            start()
        }

        binding.scannerTvTitle.text = videoTitle
        binding.scannerTvDetail.text = videoContent
        binding.scannerTvBookmark.text = videoLike.toString()
    }

    private fun setupObservers() {
        viewModel.bookmarkClicked.observe(viewLifecycleOwner) { isClicked ->
            updateBookmarkIcon(isClicked)
        }

        viewModel.bookmarkResponse.observe(viewLifecycleOwner) { responseMessage ->
            Log.d("BOOKMARK", responseMessage)
        }

        viewModel.bookmarkCount.observe(viewLifecycleOwner) { count ->
            binding.scannerTvBookmark.text = count.toString() // 북마크 수 실시간 업데이트
        }
    }

    private fun updateBookmarkIcon(isClicked: Boolean) {
        if (isClicked) {
            binding.scannerIvBookmark.setImageResource(R.drawable.scanner_bookmark_icon_cliked)
            Log.d("BOOKMARK", "북마크 아이콘: 클릭됨")
        } else {
            binding.scannerIvBookmark.setImageResource(R.drawable.scanner_bookmark_icon)
            Log.d("BOOKMARK", "북마크 아이콘: 클릭되지 않음")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
