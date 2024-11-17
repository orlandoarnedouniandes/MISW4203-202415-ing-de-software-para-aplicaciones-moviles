package com.mobileapp.mymobileapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobileapp.mymobileapp.databinding.ItemCollectorPerformerBinding
import com.mobileapp.mymobileapp.models.Performer

class CollectorPerformersAdapter(private val performers: List<Performer>) : RecyclerView.Adapter<CollectorPerformersAdapter.PerformerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformerViewHolder {
        val binding = ItemCollectorPerformerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PerformerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PerformerViewHolder, position: Int) {
        holder.bind(performers[position])
    }

    override fun getItemCount(): Int = performers.size

    class PerformerViewHolder(private val binding: ItemCollectorPerformerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(performer: Performer) {
            binding.textViewPerformerName.text = performer.name
            //  TODO: Complete HU06
        }
    }
}