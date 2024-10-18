package com.mobileapp.mymobileapp.ui.artists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobileapp.mymobileapp.databinding.FragmentArtistsBinding

class ArtistsFragment : Fragment() {

    private var _binding: FragmentArtistsBinding? = null
    private val binding get() = _binding!!

    private lateinit var artistsViewModel: ArtistsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        artistsViewModel = ViewModelProvider(this).get(ArtistsViewModel::class.java)

        _binding = FragmentArtistsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        artistsViewModel.text.observe(viewLifecycleOwner) {
            binding.textArtists.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}