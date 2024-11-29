package com.mobileapp.mymobileapp.ui.collector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileapp.mymobileapp.databinding.FragmentCollectorDetailBinding
import com.mobileapp.mymobileapp.models.CollectorAlbum
import com.mobileapp.mymobileapp.models.Performer
import com.mobileapp.mymobileapp.ui.adapters.CollectorAlbumAdapter
import com.mobileapp.mymobileapp.ui.adapters.CollectorPerformersAdapter

class CollectorDetailFragment : Fragment() {

    private var _binding: FragmentCollectorDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectorDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val collectorName = arguments?.getString("collectorName")
        val collectorFavoriteAlbums = arguments?.getParcelableArrayList<CollectorAlbum>("collectorFavoriteAlbums")
        val collectorFavoritePerformers = arguments?.getParcelableArrayList<Performer>("collectorFavoritePerformers")

        binding.textViewCollectorName.text = collectorName

        // Configurar los RecyclerViews
        binding.recyclerViewFavoritePerformers.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewFavoritePerformers.adapter = CollectorPerformersAdapter(collectorFavoritePerformers ?: listOf())

        binding.recyclerViewCollectorAlbums.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewCollectorAlbums.adapter = CollectorAlbumAdapter(collectorFavoriteAlbums ?: listOf())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
