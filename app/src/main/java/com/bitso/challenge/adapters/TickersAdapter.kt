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
) : ListAdapter<Ticker, TickersAdapter.ViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.viewBinding(ListItemTickerBinding::inflate), openTickerDetailsFn)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemTickerBinding,
        private val viewTickerDetailsFn: TickerDetailsFn?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Ticker) {
            binding.tickerTitleText.text = item.displayBook
            binding.tickerPriceText.text = binding.tickerPriceText.context.getString(R.string.price_format, item.last, item.priceCurrency)
            binding.root.setOnClickListener { viewTickerDetailsFn?.invoke(item) }
        }
    }

    private class MessageDiffCallback : DiffUtil.ItemCallback<Ticker>() {

        override fun areItemsTheSame(oldItem: Ticker, newItem: Ticker): Boolean =
            oldItem.book == newItem.book

        override fun areContentsTheSame(oldItem: Ticker, newItem: Ticker): Boolean =
            oldItem.last == newItem.last
    }
}
