package com.bitso.challenge.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bitso.challenge.databinding.ListItemTickerBinding
import com.bitso.challenge.extensions.viewBinding
import com.bitso.challenge.network.models.Ticker

typealias MovieDetailsFn = (Ticker) -> Unit

class TickersAdapter(
    private val openMovieDetailsFn: MovieDetailsFn? = null,
) : ListAdapter<Ticker, TickersAdapter.ViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.viewBinding(ListItemTickerBinding::inflate), openMovieDetailsFn)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemTickerBinding,
        private val viewTutorialDetailsFn: MovieDetailsFn?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Ticker) {
            binding.movieTitleText.text = item.last
            binding.movieImage.setImageDrawable(null)
//            val imagePath = item.posterPath
//            binding.movieImage.load("${IMAGE_BASE_URL}$imagePath")
//            binding.holder.setOnClickListener {
//                viewTutorialDetailsFn?.invoke(item)
//            }
        }
    }

    private class MessageDiffCallback : DiffUtil.ItemCallback<Ticker>() {

        override fun areItemsTheSame(oldItem: Ticker, newItem: Ticker): Boolean =
            oldItem.book == newItem.book

        override fun areContentsTheSame(oldItem: Ticker, newItem: Ticker): Boolean =
            oldItem == newItem
    }
}
