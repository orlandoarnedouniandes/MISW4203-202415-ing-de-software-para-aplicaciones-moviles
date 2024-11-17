package com.mobileapp.mymobileapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobileapp.mymobileapp.databinding.ItemAlbumBinding
import com.mobileapp.mymobileapp.databinding.ItemCancionAlbumBinding
import com.mobileapp.mymobileapp.models.Album
import com.mobileapp.mymobileapp.models.Track
import com.mobileapp.mymobileapp.ui.albums.AlbumDetailFragment
import com.mobileapp.mymobileapp.util.DateUtils


class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    private val albums = mutableListOf<Album>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albums[position])
        holder.itemView.setOnClickListener {
            Log.d("AlbumsAdapter", "Item clicked: ${albums[position].name}")

            val album = albums[position]

            // Create the new fragment and pass data
            AlbumDetailFragment.newInstance(
                album,
                holder
            )

        }
    }

    override fun getItemCount() = albums.size

    fun submitList(albumList: List<Album>) {
        albums.clear()
        albums.addAll(albumList)
        notifyDataSetChanged()
    }

    class AlbumViewHolder(private val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.textViewAlbumName.text = album.name
            binding.textViewReleaseDate.text = DateUtils.extractYear(album.releaseDate.toString())
            binding.textViewArtistName.text = album.performers.get(0).name
            Glide.with(binding.root.context)
                .load(album.cover)
                .apply(RequestOptions()
                    .centerCrop())
                .into(binding.imageViewCover)
        }
    }
}

class TracksAdapter(trackspar: List<Track>) : RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {

    private val tracks = mutableListOf<Track>()
    init {
        Log.i("info canciones", trackspar.toString())
        tracks.clear()
        tracks.addAll(trackspar)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TracksAdapter.TracksViewHolder {
        val binding = ItemCancionAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TracksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TracksAdapter.TracksViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    class TracksViewHolder(private val binding: ItemCancionAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(track : Track){
            binding.textViewSongName.text = track.name
            binding.textViewDuration.text = track.duration
        }
    }

}