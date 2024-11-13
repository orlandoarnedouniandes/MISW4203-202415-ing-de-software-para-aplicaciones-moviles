package com.mobileapp.mymobileapp.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobileapp.mymobileapp.databinding.ItemArtistBinding
import com.mobileapp.mymobileapp.models.Artist
import com.mobileapp.mymobileapp.util.DateUtils
import com.squareup.picasso.Picasso
import java.util.Locale

class ArtistsAdapter(
    private var artists: List<Artist>, private val itemClickListener: OnItemClickListener
): RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>() {

    private var filteredArtists : List<Artist> = artists

    interface OnItemClickListener {
        fun onItemClick(artist: Artist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = ItemArtistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(filteredArtists[position])
    }

    override fun getItemCount(): Int = filteredArtists.size

    fun updateArtists(artistList: List<Artist>) {
        artists = artistList
        filteredArtists + artistList
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        filteredArtists = if (query.isEmpty()) {
            artists
        } else {
            artists.filter {
                it.name.lowercase(Locale.getDefault())
                    .contains(query.lowercase())
            }
        }
        notifyDataSetChanged()
    }

    inner class ArtistViewHolder(private val binding: ItemArtistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist) {
            Picasso.get().load(artist.image).into(binding.imageViewArtist)
            binding.textViewArtistName.text = artist.name
            binding.textViewArtistDescription.text = if (artist.description.length > 50) {
                artist.description.substring(0, 50) + "..."
            } else {
                artist.description
            }
            binding.textViewArtistBirthDate.text = DateUtils.extractYear(artist.birthDate.toString())

            binding.root.setOnClickListener {
                itemClickListener.onItemClick(artist)
            }
        }
    }
}