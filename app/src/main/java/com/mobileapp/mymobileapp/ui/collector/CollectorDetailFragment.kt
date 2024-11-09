// CollectorDetailFragment.kt
package com.mobileapp.mymobileapp.ui.collector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobileapp.mymobileapp.databinding.FragmentCollectorDetailBinding

class CollectorDetailFragment : Fragment() {

    private var _binding: FragmentCollectorDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectorDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val collectorName = arguments?.getString("collectorName")
        activity?.title = collectorName

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}