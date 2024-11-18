package com.mobileapp.mymobileapp.ui.artists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobileapp.mymobileapp.databinding.FragmentArtistDetailBinding
import com.mobileapp.mymobileapp.models.Artist


class ArtistDetailFragment : Fragment() {
    private var _binding: FragmentArtistDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.artist = arguments?.getParcelable<Artist>("artist")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}