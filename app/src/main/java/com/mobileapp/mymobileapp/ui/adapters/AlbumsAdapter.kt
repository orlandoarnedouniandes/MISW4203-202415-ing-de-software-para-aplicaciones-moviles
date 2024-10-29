package com.mobileapp.mymobileapp.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobileapp.mymobileapp.databinding.ItemAlbumBinding
import com.mobileapp.mymobileapp.models.Album
import com.mobileapp.mymobileapp.util.DateUtils


class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    private val albums = mutableListOf<Album>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    override fun getItemCount() = albums.size

    fun submitList(albumList: List<Album>) {
        albums.clear()
        albums.addAll(albumList)
        notifyDataSetChanged()
    }

    class AlbumViewHolder(private val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            // Bind album data to views
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