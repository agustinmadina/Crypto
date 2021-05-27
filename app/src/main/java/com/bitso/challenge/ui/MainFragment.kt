package com.bitso.challenge.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bitso.challenge.R
import com.bitso.challenge.adapters.TickersAdapter
import com.bitso.challenge.databinding.FragmentMainBinding
import com.bitso.challenge.extensions.*
import com.bitso.challenge.network.models.Ticker
import com.bitso.challenge.viewmodels.TickersState
import com.bitso.challenge.viewmodels.TickersViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainFragment : Fragment(R.layout.fragment_main) {
    private val tickersViewModel: TickersViewModel by viewModel()
    private val binding by viewBinding(FragmentMainBinding::bind)

    private lateinit var tickersAdapter: TickersAdapter

    var timer: Timer? = null
    var timerTask: TimerTask? = null
    var position = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        swipeRefreshLayout.setOnRefreshListener {
            tickersViewModel.getAllTickers()
        }
        tickersViewModel.getAllTickersEvery30Secs()
    }

    private fun setupRecyclerView() {
        var layoutManager = GridLayoutManager(context, 2)
        binding.tickersRecyclerView.layoutManager = layoutManager
        (binding.tickersRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        tickersAdapter = TickersAdapter(
            openTickerDetailsFn = { ticker ->
                openTickerDetails(ticker)
            }
        )
        binding.tickersRecyclerView.adapter = tickersAdapter
    }

    private fun openTickerDetails(ticker: Ticker) {
        val directions = MainFragmentDirections.showTickerDetail(ticker)
        findNavController().navigate(directions)
    }

    private fun setupObservers() {
        tickersViewModel.tickersState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is TickersState.Loading -> {
                    binding.loadingProgressBar.show()
                    binding.retryTextView.hide()
                }
                is TickersState.Success -> {
                    binding.loadingProgressBar.hide()
                    binding.retryTextView.hide()
                    swipeRefreshLayout.isRefreshing = false
                }
                is TickersState.Error -> {
                    binding.loadingProgressBar.hide()
                    binding.retryTextView.show()
                    swipeRefreshLayout.isRefreshing = false
                    binding.retryTextView.setOnClickListener { tickersViewModel.getAllTickers() }
                    val message = state.exception.getIOErrorMessage(requireContext())
                    requireContext().showToast(message)
                }
            }
        }

        tickersViewModel.tickers.observe(viewLifecycleOwner) { tickers ->
            tickersAdapter.submitList(tickers)
        }
    }
}