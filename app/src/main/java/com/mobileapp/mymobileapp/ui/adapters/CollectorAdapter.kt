package com.mobileapp.mymobileapp.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobileapp.mymobileapp.R
import com.mobileapp.mymobileapp.databinding.ItemCollectorBinding
import com.mobileapp.mymobileapp.models.Collector

class CollectorAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CollectorAdapter.CollectorViewHolder>() {

    private var collectors: List<Collector> = emptyList()
    private var filteredCollectors: List<Collector> = emptyList()

    interface OnItemClickListener {
        fun onItemClick(collector: Collector)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val binding = ItemCollectorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.bind(filteredCollectors[position])
    }

    override fun getItemCount(): Int = filteredCollectors.size

    fun updateCollectors(newCollectors: List<Collector>) {
        val diffResult = DiffUtil.calculateDiff(CollectorDiffCallback(collectors, newCollectors))
        collectors = newCollectors
        filteredCollectors = newCollectors
        diffResult.dispatchUpdatesTo(this)
    }

    fun filter(query: String) {
        filteredCollectors = if (query.isBlank()) {
            collectors
        } else {
            collectors.filter { it.name.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

    inner class CollectorViewHolder(private val binding: ItemCollectorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(collector: Collector) {
            with(binding) {
                // Load collector image with Glide
                val imageUrl = collector.favoritePerformers.firstOrNull()?.image
                Glide.with(root.context)
                    .load(imageUrl)
                    .placeholder(android.R.color.darker_gray)
                    .error(android.R.drawable.stat_notify_error)
                    .centerCrop()
                    .into(imageViewCollectorPhoto)

                textViewCollectorName.text = collector.name
                textViewCollectorEmail.text = collector.email
                textViewCollectorTelephone.text = collector.telephone
                textViewTotalAlbums.text = root.context.getString(
                    R.string.total_albums,
                    collector.collectorAlbums.size
                )
                root.setOnClickListener {
                    val bundle = Bundle().apply {
                        putString("collectorName", collector.name)
                    }
                    itemClickListener.onItemClick(collector)
                }
            }
        }
    }

    private class CollectorDiffCallback(
        private val oldList: List<Collector>,
        private val newList: List<Collector>
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