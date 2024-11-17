package com.mobileapp.mymobileapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
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

    fun submitList(albumList: List<Album>) {
        val diffCallback = AlbumDiffCallback(albums, albumList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        albums.clear()
        albums.addAll(albumList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albums[position])
        holder.itemView.setOnClickListener {
            Log.d("AlbumsAdapter", "Item clicked: ${albums[position].name}")

            val album = albums[position]
            AlbumDetailFragment.newInstance(album, holder)
        }
    }

    override fun getItemCount() = albums.size


    class AlbumViewHolder(private val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.textViewAlbumName.text = album.name
            binding.textViewReleaseDate.text = DateUtils.extractYear(album.releaseDate.toString())
            binding.textViewArtistName.text = album.performers.firstOrNull()?.name ?: "unknown_performer"
            Glide.with(binding.root.context)
                .load(album.cover)
                .apply(
                    RequestOptions()
                        .placeholder(android.R.color.darker_gray)
                        .error(android.R.drawable.stat_notify_error)
                        .centerCrop()
                )
                .into(binding.imageViewCover)
        }
    }

    // DiffUtil callback for Albums
    class AlbumDiffCallback(private val oldList: List<Album>, private val newList: List<Album>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}


class TracksAdapter(private val tracksList: List<Track>) : RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {

    private val tracks = tracksList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val binding = ItemCancionAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TracksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount(): Int = tracks.size

    class TracksViewHolder(private val binding: ItemCancionAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            binding.textViewSongName.text = track.name
            binding.textViewDuration.text = track.duration
        }
    }
}