package com.betsson.interviewtest.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.betsson.interviewtest.databinding.ListItemBinding
import com.betsson.interviewtest.domain.entity.Bet
import com.bumptech.glide.Glide

class ItemAdapter : ListAdapter<Bet, ItemAdapter.ViewHolder>(DIFF_UTIL) {

    companion object {
        private val DIFF_UTIL = object : androidx.recyclerview.widget.DiffUtil.ItemCallback<Bet>() {

            override fun areItemsTheSame(oldItem: Bet, newItem: Bet): Boolean {
                return oldItem.type == newItem.type
            }

            override fun areContentsTheSame(oldItem: Bet, newItem: Bet): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(
        val binding: ListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Bet) {
            binding.betType.text = item.type
            binding.betSellIn.text = item.sellIn.toString()
            binding.betOdds.text = item.odds.toString()

            Glide
                .with(binding.root.context)
                .load(item.image)
                .centerCrop()
                .into(binding.betImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}