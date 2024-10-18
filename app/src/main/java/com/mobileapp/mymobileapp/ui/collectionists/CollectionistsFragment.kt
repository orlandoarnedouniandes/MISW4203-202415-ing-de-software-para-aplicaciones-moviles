package com.mobileapp.mymobileapp.ui.collectionists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobileapp.mymobileapp.databinding.FragmentCollectionistsBinding

class CollectionistsFragment : Fragment() {

    private var _binding: FragmentCollectionistsBinding? = null
    private val binding get() = _binding!!

    private lateinit var collectionistsViewModel: CollectionistsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        collectionistsViewModel = ViewModelProvider(this).get(CollectionistsViewModel::class.java)

        _binding = FragmentCollectionistsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        collectionistsViewModel.text.observe(viewLifecycleOwner) {
            binding.textCollectionists.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}