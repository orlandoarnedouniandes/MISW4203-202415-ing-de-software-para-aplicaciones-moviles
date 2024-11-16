package com.mobileapp.mymobileapp.ui.artists

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater

import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.adapters.SearchViewBindingAdapter.OnQueryTextSubmit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileapp.mymobileapp.R
import com.mobileapp.mymobileapp.data.repositories.ArtistRepository
import com.mobileapp.mymobileapp.database.ArtistDatabase
import com.mobileapp.mymobileapp.databinding.FragmentArtistsBinding
import com.mobileapp.mymobileapp.databinding.FragmentCollectorBinding
import com.mobileapp.mymobileapp.models.Artist
import com.mobileapp.mymobileapp.network.ArtistsApi
import com.mobileapp.mymobileapp.network.RetrofitClient
import com.mobileapp.mymobileapp.ui.adapters.ArtistsAdapter

class ArtistsFragment : Fragment(R.layout.fragment_artists) {

    private var _binding: FragmentArtistsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ArtistsAdapter
    private lateinit var viewModel: ArtistsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewArtists.layoutManager = layoutManager

        viewModel.artists.observe(viewLifecycleOwner, { artistList ->
            if(artistList != null){
                adapter = ArtistsAdapter(artistList,object : ArtistsAdapter.OnItemClickListener {
                    override fun onItemClick(artist: Artist) {
                        val bundle = Bundle()
                        bundle.putParcelable("artist",artist)
                        findNavController().navigate(R.id.action_navigation_artists_to_artistDetailFragment, bundle)
                    }
                })
                binding.recyclerViewArtists.adapter = adapter
                setupSearchView()
            }else{
                Log.d("ArtistsFragment", "No artists found")
            }
        })

    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query:String?):Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
