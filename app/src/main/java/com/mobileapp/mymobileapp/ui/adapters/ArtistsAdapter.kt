package com.mobileapp.mymobileapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobileapp.mymobileapp.R
import com.mobileapp.mymobileapp.databinding.ItemArtistBinding
import com.mobileapp.mymobileapp.models.Artist
import com.mobileapp.mymobileapp.util.DateUtils
import java.util.*

class ArtistsAdapter(
    private var artists: List<Artist>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>() {

    private var filteredArtists: List<Artist> = artists

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
        val diffCallback = ArtistDiffCallback(filteredArtists, artistList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        artists = artistList
        filteredArtists = artistList
        diffResult.dispatchUpdatesTo(this)
    }

    fun filter(query: String) {
        filteredArtists = if (query.isBlank()) {
            artists
        } else {
            artists.filter {
                it.name.lowercase(Locale.getDefault())
                    .contains(query.lowercase(Locale.getDefault()))
            }
        }
        notifyDataSetChanged()
    }

    inner class ArtistViewHolder(private val binding: ItemArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(artist: Artist) {
            with(binding) {
                // Load image using Glide with a placeholder and error image
                Glide.with(root.context)
                    .load(artist.image)
                    .apply(
                        RequestOptions()
                            .placeholder(android.R.color.darker_gray)
                            .error(android.R.drawable.stat_notify_error)
                            .centerCrop()
                    )
                    .into(imageViewArtist)
                textViewArtistName.text = artist.name
                textViewArtistDescription.text = artist.description.let {
                    if (it.length > 50) "${it.substring(0, 50)}..." else it
                }
                textViewArtistBirthDate.text = DateUtils.extractYear(artist.birthDate.toString())
                root.setOnClickListener {
                    itemClickListener.onItemClick(artist)
                }
            }
        }
    }

    class ArtistDiffCallback(
        private val oldList: List<Artist>,
        private val newList: List<Artist>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}