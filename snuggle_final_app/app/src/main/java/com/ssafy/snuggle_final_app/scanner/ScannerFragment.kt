package com.ssafy.snuggle_final_app.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.databinding.FragmentScannerBinding


class ScannerFragment : Fragment() {
    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)

        // TODO: NFC 스캔 후 해당하는 제품 영상으로 이동하는 것으로 수정해야함
        binding.scannerLlReadypage.setOnClickListener {
            val bannerTransaction = parentFragmentManager.beginTransaction()
            bannerTransaction.replace(R.id.main_frameLayout, ScannerVideoFragment())
            bannerTransaction.addToBackStack(null)
            bannerTransaction.commit()
        }

        

        return binding.root
    }


}