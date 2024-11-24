package com.mobileapp.mymobileapp.ui.albums

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mobileapp.mymobileapp.R
import com.mobileapp.mymobileapp.models.Album
import com.mobileapp.mymobileapp.models.Track
import com.mobileapp.mymobileapp.ui.adapters.AlbumsAdapter
import com.mobileapp.mymobileapp.ui.adapters.TracksAdapter
import com.mobileapp.mymobileapp.util.DateUtils

class AlbumDetailFragment : Fragment(R.layout.fragment_album_detail) {

    //private lateinit var binding: FragmentArtistDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding = FragmentArtistDetailBinding.bind(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_album_detail, container, false)
        Log.e("","Paso - carga info")
        // Retrieve data passed through arguments
        val albumName = arguments?.getString("name")
        val albumCover = arguments?.getString("cover")
        val albumArtist = arguments?.getString("artist")
        val albumYear = arguments?.getString("year")
        val albumDescription = arguments?.getString("description")
        val canciones = arguments?.getString("songs")

        // Set the data in the UI
        view.findViewById<TextView>(R.id.albumNameTextView).text = albumName

        val imageView = view.findViewById<ImageView>(R.id.albumImageView)
        Glide.with(this).load(albumCover).into(imageView)

        view.findViewById<TextView>(R.id.artistNameTextView).text = albumArtist
        view.findViewById<TextView>(R.id.albumYearTextView).text = albumYear
        view.findViewById<TextView>(R.id.albumDescriptionTextView).text = albumDescription

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        if (canciones != null) {
            val gson = Gson()
            val tracks : List<Track> = gson.fromJson(canciones, Array<Track>::class.java).toList()
            //Log.d("AlbumDetailFragment", "Name: ${tracks.name}, Artist: ${tracks.duration}")
            recyclerView.adapter = TracksAdapter(tracks)
        }

        return view
    }

    companion object {
        fun newInstance(album: Album, holder: AlbumsAdapter.AlbumViewHolder) {

            val navController = Navigation.findNavController(holder.itemView)

            val bundle = Bundle().apply {
                putString("name", album.name)
                putString("cover", album.cover)
                val performerName = album.performers.firstOrNull()?.name ?: "Unknown Performer"
                putString("artist", performerName)
                putString("year", DateUtils.extractYear(album.releaseDate.toString()))
                putString("description", album.description)
                val gson = Gson()
                var songJson = gson.toJson(album.tracks)
                putString("songs", songJson.toString())
                Log.i("info canciones", songJson.toString())
            }
            navController.navigate(R.id.albumDetailFragment, bundle)
        }
    }
}