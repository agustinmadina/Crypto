package com.bitso.challenge.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bitso.challenge.R
import com.bitso.challenge.databinding.ListItemTickerBinding
import com.bitso.challenge.extensions.viewBinding
import com.bitso.challenge.network.models.Ticker

typealias TickerDetailsFn = (Ticker) -> Unit

class TickersAdapter(
    private val openTickerDetailsFn: TickerDetailsFn? = null,
) : ListAdapter<Ticker, TickersAdapter.TickerViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TickerViewHolder =
        TickerViewHolder(parent.viewBinding(ListItemTickerBinding::inflate), openTickerDetailsFn)

    override fun onBindViewHolder(holder: TickerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TickerViewHolder(
        private val binding: ListItemTickerBinding,
        private val viewTickerDetailsFn: TickerDetailsFn? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Ticker) {
            binding.tickerTitleText.text = item.displayBook
            binding.tickerPriceText.text = binding.tickerPriceText.context.getString(R.string.price_format, item.last, item.priceCurrency)
            binding.holder.setOnClickListener { viewTickerDetailsFn?.invoke(item) }
        }
    }

    private class MessageDiffCallback : DiffUtil.ItemCallback<Ticker>() {

        override fun areItemsTheSame(oldItem: Ticker, newItem: Ticker): Boolean =
            oldItem.book == newItem.book

        override fun areContentsTheSame(oldItem: Ticker, newItem: Ticker): Boolean =
            oldItem.last == newItem.last
    }
}
