package com.mobileapp.mymobileapp.ui.artists

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileapp.mymobileapp.R
import com.mobileapp.mymobileapp.data.repositories.ArtistRepository
import com.mobileapp.mymobileapp.database.ArtistDatabase
import com.mobileapp.mymobileapp.databinding.FragmentArtistsBinding
import com.mobileapp.mymobileapp.models.Artist
import com.mobileapp.mymobileapp.network.ArtistsApi
import com.mobileapp.mymobileapp.network.RetrofitClient
import com.mobileapp.mymobileapp.ui.adapters.ArtistsAdapter

class ArtistsFragment : Fragment(R.layout.fragment_artists) {

    private lateinit var binding: FragmentArtistsBinding
    private lateinit var adapter: ArtistsAdapter
    private lateinit var viewModel: ArtistsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArtistsBinding.bind(view)

        // Retrofit API
        val api = RetrofitClient.instance.create(ArtistsApi::class.java)

        // Room Database and DAO
        val artistDao = ArtistDatabase.getDatabase(requireContext()).artistDao()

        // Repository
        val repository = ArtistRepository(api, artistDao)

        // ViewModel Factory
        val factory = ArtistsViewModelFactory(repository)

        // ViewModel
        viewModel = ViewModelProvider(this, factory).get(ArtistsViewModel::class.java)

        setupRecyclerView()

        viewModel.artists.observe(viewLifecycleOwner, Observer { artistList ->
            adapter.submitList(artistList)
        })

        viewModel.fetchArtists()
    }

    private fun setupRecyclerView() {
        adapter = ArtistsAdapter(object : ArtistsAdapter.OnItemClickListener {
            override fun onItemClick(artist: Artist) {
                val bundle = Bundle()
                bundle.putParcelable("artist",artist)

                findNavController().navigate(R.id.action_navigation_artists_to_artistDetailFragment, bundle)
            }
        })
        binding.recyclerViewArtists.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ArtistsFragment.adapter
        }
    }
}
