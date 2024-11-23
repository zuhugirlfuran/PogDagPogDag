package com.ssafy.snuggle_final_app.scanner

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Tagging
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import com.ssafy.snuggle_final_app.data.service.TaggingService
import com.ssafy.snuggle_final_app.databinding.FragmentScannerBinding
import kotlinx.coroutines.launch
import retrofit2.Call


class ScannerFragment : Fragment() {
    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)

        return binding.root
    }

    //== tag 데이터에 따라서 fragment 전환하기 ==//
    fun handleNfcTag(payload: String) {
        Log.d("NFC_TAG", "handleNfcTag 호출됨, payload: $payload")
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val taggingData = RetrofitUtil.taggingService.getTaggingById(payload)
                Log.d("NFC_TAG", "Retrofit 요청 성공: $taggingData")
                taggingData?.let {
                    val videoFragment = ScannerVideoFragment.newInstance(
                        it.videoSrc,
                        it.videoTitle,
                        it.videoContent,
                        it.videoLike,
                        it.taggingId
                    )
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main_frameLayout, videoFragment)
                        .addToBackStack(null)
                        .commit()
                }
            } catch (e: Exception) {
                Log.e("NFC_TAG", "Retrofit 요청 실패: ${e.message}")
            }
        }
    }

}