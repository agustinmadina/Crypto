package com.bitso.challenge.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.bitso.challenge.adapters.TickersAdapter
import com.bitso.challenge.databinding.ViewInfiniteRotationBinding
import kotlinx.coroutines.delay

private const val DELAY_BETWEEN_SCROLL_MS = 25L
private const val SCROLL_DX = 5
private const val DIRECTION_RIGHT = 1

class InfiniteRotationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = ViewInfiniteRotationBinding.inflate(LayoutInflater.from(context), this)

    private val layoutManager: LinearLayoutManager = LinearLayoutManager(context, HORIZONTAL, false)

    fun setAdapter(adapter: TickersAdapter) {
        val recyclerView = binding.recyclerViewHorizontalList
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    tailrec suspend fun autoScrollFeaturesList() {
        if (binding.recyclerViewHorizontalList.canScrollHorizontally(DIRECTION_RIGHT)) {
            binding.recyclerViewHorizontalList.smoothScrollBy(SCROLL_DX, 0)
        } else {
            val firstPosition =
                (binding.recyclerViewHorizontalList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (firstPosition != RecyclerView.NO_POSITION) {
                val adapter = binding.recyclerViewHorizontalList.adapter as TickersAdapter
                val currentList = adapter.currentList
                val secondPart = currentList.subList(0, firstPosition)
                val firstPart = currentList.subList(firstPosition, currentList.size)
                adapter.submitList(firstPart + secondPart)
            }
        }
        delay(DELAY_BETWEEN_SCROLL_MS)
        autoScrollFeaturesList()
    }
}