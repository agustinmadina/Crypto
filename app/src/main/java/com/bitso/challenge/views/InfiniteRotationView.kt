package com.bitso.challenge.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.bitso.challenge.adapters.TickersAdapter
import com.bitso.challenge.databinding.ViewInfiniteRotationBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InfiniteRotationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = ViewInfiniteRotationBinding.inflate(LayoutInflater.from(context), this)

    private val layoutManager: LinearLayoutManager = LinearLayoutManager(context, HORIZONTAL, false)
    private lateinit var onScrollListener: OnScrollListener

    fun setAdapter(adapter: TickersAdapter) {
        val recyclerView = binding.recyclerViewHorizontalList
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        onScrollListener = OnScrollListener(layoutManager)
        recyclerView.addOnScrollListener(onScrollListener)
    }

    fun autoScroll(intervalInMillis: Long) {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            while (true) {
                delay(intervalInMillis)
                binding.recyclerViewHorizontalList.smoothScrollBy(40, 0)
            }
        }
    }

    class OnScrollListener(
        private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

        private var loading = true
        private var pastVisibleItems = 0
        var visibleItemCount: Int = 0
        var totalItemCount: Int = 0

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dx > 0) {
                visibleItemCount = layoutManager.childCount;
                totalItemCount = layoutManager.itemCount;
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                if (loading) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        loading = false
                        recyclerView.scrollToPosition(0)
                        loading = true
                    }
                }
            }
        }
    }
}