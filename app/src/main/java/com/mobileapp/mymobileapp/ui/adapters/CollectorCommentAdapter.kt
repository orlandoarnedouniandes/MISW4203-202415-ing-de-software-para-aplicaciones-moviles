package com.mobileapp.mymobileapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobileapp.mymobileapp.databinding.ItemCollectorCommentsBinding
import com.mobileapp.mymobileapp.models.Comment

class CollectorCommentAdapter(private val comments: List<Comment>) : RecyclerView.Adapter<CollectorCommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCollectorCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int = comments.size

    class CommentViewHolder(private val binding: ItemCollectorCommentsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.textViewCollectorCommentsComment.text = comment.description
            // TODO: Complete HU06
        }
    }
}