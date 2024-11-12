package com.mobileapp.mymobileapp.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mobileapp.mymobileapp.R
import com.mobileapp.mymobileapp.databinding.ItemCollectorBinding
import com.mobileapp.mymobileapp.models.Collector
import com.squareup.picasso.Picasso
import java.util.Locale

class CollectorAdapter(
    private var collectors: List<Collector>, private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CollectorAdapter.CollectorViewHolder>() {

    private var filteredCollectors: List<Collector> = collectors

    interface OnItemClickListener {
        fun onItemClick(collector: Collector)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val binding =
            ItemCollectorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.bind(filteredCollectors[position])
    }

    override fun getItemCount(): Int = filteredCollectors.size

    fun updateCollectors(newCollectors: List<Collector>) {
        collectors = newCollectors
        filteredCollectors = newCollectors
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        filteredCollectors = if (query.isEmpty()) {
            collectors
        } else {
            collectors.filter {
                it.name.lowercase(Locale.getDefault())
                    .contains(query.lowercase(Locale.getDefault()))
            }
        }
        notifyDataSetChanged()
    }

    inner class CollectorViewHolder(private val binding: ItemCollectorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(collector: Collector) {
            Picasso.get().load(collector.favoritePerformers.first().image)
                .into(binding.imageViewCollectorPhoto)
            binding.textViewCollectorName.text = collector.name
            binding.textViewCollectorEmail.text = collector.email
            binding.textViewCollectorTelephone.text = collector.telephone
            val totalAlbumsFormatted = String.format(
                binding.root.context.getString(R.string.total_albums),
                collector.collectorAlbums.size
            )
            binding.textViewTotalAlbums.text = totalAlbumsFormatted

            binding.root.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("collectorName", collector.name)
                }
                itemClickListener.onItemClick(collector)
            }
        }
    }
}

