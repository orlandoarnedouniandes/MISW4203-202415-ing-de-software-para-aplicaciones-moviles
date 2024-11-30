package com.mobileapp.mymobileapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobileapp.mymobileapp.databinding.ItemCollectorAlbumBinding
import com.mobileapp.mymobileapp.models.CollectorAlbum
import com.mobileapp.mymobileapp.util.DateUtils

class CollectorAlbumAdapter (private val collectorAlbums: List<CollectorAlbum>) : RecyclerView.Adapter<CollectorAlbumAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemCollectorAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(collectorAlbums[position])
    }

    override fun getItemCount(): Int = collectorAlbums.size

    class AlbumViewHolder(private val binding: ItemCollectorAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(collectorAlbum: CollectorAlbum) {
            binding.textViewAlbumName.text = collectorAlbum.album.name
            binding.textViewAlbumYear.text = DateUtils.extractYear(collectorAlbum.album.releaseDate)
            Glide.with(binding.root.context).load(collectorAlbum.album.cover).into(binding.imageViewAlbum)
        }
    }
}