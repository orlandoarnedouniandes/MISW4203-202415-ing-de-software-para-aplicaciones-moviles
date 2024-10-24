package com.mobileapp.mymobileapp.ui.albums

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileapp.mymobileapp.R
import com.mobileapp.mymobileapp.databinding.FragmentAlbumsBinding
import com.mobileapp.mymobileapp.ui.adapters.AlbumsAdapter

class AlbumsFragment : Fragment(R.layout.fragment_albums) {

    private val viewModel: AlbumsViewModel by viewModels()
    private lateinit var binding: FragmentAlbumsBinding
    private lateinit var adapter: AlbumsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlbumsBinding.bind(view)

        setupRecyclerView()

        viewModel.albums.observe(viewLifecycleOwner, Observer { albumList ->
            adapter.submitList(albumList)
        })

        viewModel.fetchAlbums()
    }

    private fun setupRecyclerView() {
        adapter = AlbumsAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@AlbumsFragment.adapter
        }
    }
}