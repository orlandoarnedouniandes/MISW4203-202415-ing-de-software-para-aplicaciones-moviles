package com.mobileapp.mymobileapp.ui.collector

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileapp.mymobileapp.R
import com.mobileapp.mymobileapp.data.repositories.CollectorRepository
import com.mobileapp.mymobileapp.database.CollectorDatabase
import com.mobileapp.mymobileapp.databinding.FragmentCollectorBinding
import com.mobileapp.mymobileapp.models.Collector
import com.mobileapp.mymobileapp.network.CollectorsApi
import com.mobileapp.mymobileapp.network.RetrofitClient
import com.mobileapp.mymobileapp.ui.adapters.CollectorAdapter

class CollectorFragment : Fragment(R.layout.fragment_collector) {

    private var _binding: FragmentCollectorBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CollectorViewModel
    private lateinit var adapter: CollectorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Collectors"
        val apiService = RetrofitClient.instance.create(CollectorsApi::class.java)
        val database = CollectorDatabase.getDatabase(requireContext().applicationContext)
        val repository = CollectorRepository(apiService, database)
        val factory = CollectorViewModelFactory(requireActivity().application, repository)

        viewModel = ViewModelProvider(this, factory).get(CollectorViewModel::class.java)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewCollectors.layoutManager = layoutManager

        adapter = CollectorAdapter(object : CollectorAdapter.OnItemClickListener {
            override fun onItemClick(collector: Collector) {
                val bundle = Bundle().apply {
                    putString("collectorName", collector.name)
                    putString("collectorTelephone", collector.telephone)
                    putString("collectorEmail", collector.email)
                    putParcelableArrayList("collectorComments", ArrayList(collector.comments))
                    putParcelableArrayList("collectorFavoriteAlbums", ArrayList(collector.collectorAlbums))
                    putParcelableArrayList("collectorFavoritePerformers", ArrayList(collector.favoritePerformers))
                }
                findNavController().navigate(R.id.action_navigation_collectors_to_collectorDetailFragment, bundle)
            }
        })
        binding.recyclerViewCollectors.adapter = adapter
        viewModel.collectors.observe(viewLifecycleOwner) { collectors ->
            if (collectors != null) {
                adapter.updateCollectors(collectors)
            } else {
                Log.d("CollectorFragment", "No collectors found")
            }
        }
        setupSearchView()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
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