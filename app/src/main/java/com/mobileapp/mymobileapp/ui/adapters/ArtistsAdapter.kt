package com.mobileapp.mymobileapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobileapp.mymobileapp.databinding.ItemArtistBinding
import com.mobileapp.mymobileapp.models.Artist
import com.mobileapp.mymobileapp.util.DateUtils

class ArtistsAdapter : RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>() {

    private val artists = mutableListOf<Artist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = ItemArtistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(artists[position])
    }

    override fun getItemCount(): Int = artists.size

    fun submitList(artistList: List<Artist>) {
        artists.clear()
        artists.addAll(artistList)
        notifyDataSetChanged()
    }

    class ArtistViewHolder(private val binding: ItemArtistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist) {
            binding.textViewArtistName.text = artist.name
            binding.textViewArtistDescription.text = if (artist.description.length > 50) {
                artist.description.substring(0, 50) + "..."
            } else {
                artist.description
            }

            binding.textViewArtistBirthDate.text = DateUtils.extractYear(artist.birthDate.toString())

            Glide.with(binding.root.context)
                .load(artist.image)
                .apply(RequestOptions()
                    .centerCrop())
            .into(binding.imageViewArtist)
        }
    }
}