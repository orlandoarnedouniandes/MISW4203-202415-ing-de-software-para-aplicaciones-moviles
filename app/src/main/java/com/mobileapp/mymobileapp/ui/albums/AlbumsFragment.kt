package com.mobileapp.mymobileapp.ui.albums

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileapp.mymobileapp.R
import com.mobileapp.mymobileapp.data.repositories.AlbumRepository
import com.mobileapp.mymobileapp.database.AlbumDatabase
import com.mobileapp.mymobileapp.databinding.FragmentAlbumsBinding
import com.mobileapp.mymobileapp.network.AlbumsApi
import com.mobileapp.mymobileapp.network.RetrofitClient
import com.mobileapp.mymobileapp.ui.adapters.AlbumsAdapter

class AlbumsFragment : Fragment(R.layout.fragment_albums) {

    private lateinit var binding: FragmentAlbumsBinding
    private lateinit var adapter: AlbumsAdapter
    private lateinit var viewModel: AlbumsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlbumsBinding.bind(view)

        // Retrofit API
        val api = RetrofitClient.instance.create(AlbumsApi::class.java)

        // Room Database and DAO
        val albumDao = AlbumDatabase.getDatabase(requireContext()).albumDao()

        // Repository
        val repository = AlbumRepository(api, albumDao)

        // ViewModel Factory
        val factory = AlbumsViewModelFactory(repository)

        // ViewModel
        viewModel = ViewModelProvider(this, factory).get(AlbumsViewModel::class.java)

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