package com.mobileapp.mymobileapp.ui.collector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileapp.mymobileapp.R
import com.mobileapp.mymobileapp.databinding.FragmentCollectorDetailBinding
import com.mobileapp.mymobileapp.models.CollectorAlbum
import com.mobileapp.mymobileapp.models.Comment
import com.mobileapp.mymobileapp.models.Performer
import com.mobileapp.mymobileapp.ui.adapters.CollectorAlbumAdapter
import com.mobileapp.mymobileapp.ui.adapters.CollectorCommentAdapter
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
        val collectorTelephone = arguments?.getString("collectorTelephone")
        val collectorEmail = arguments?.getString("collectorEmail")
        val collectorComments = arguments?.getParcelableArrayList<Comment>("collectorComments")
        val collectorFavoriteAlbums = arguments?.getParcelableArrayList<CollectorAlbum>("collectorFavoriteAlbums")
        val collectorFavoritePerformers = arguments?.getParcelableArrayList<Performer>("collectorFavoritePerformers")

        binding.textViewCollectorTelephone.text = collectorTelephone
        binding.textViewCollectorEmail.text = collectorEmail

        // Collectors favorite performers
        binding.recyclerViewFavoritePerformers.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFavoritePerformers.adapter = CollectorPerformersAdapter(collectorFavoritePerformers ?: listOf())
        // Collectors favorite albums
        binding.recyclerViewCollectorAlbums.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewCollectorAlbums.adapter = CollectorAlbumAdapter(collectorFavoriteAlbums ?: listOf())
        // Collectors comments
        binding.recyclerViewComments.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewComments.adapter = CollectorCommentAdapter(collectorComments ?: listOf())

        // Set the label of the fragment to the collector's name
        findNavController().addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.collectorDetailFragment) {
                destination.label = collectorName
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}