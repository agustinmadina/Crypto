package com.bitso.challenge.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitso.challenge.R
import com.bitso.challenge.adapters.TickersAdapter
import com.bitso.challenge.databinding.FragmentMainBinding
import com.bitso.challenge.extensions.getIOErrorMessage
import com.bitso.challenge.extensions.showToast
import com.bitso.challenge.extensions.viewBinding
import com.bitso.challenge.network.models.Ticker
import com.bitso.challenge.viewmodels.TickersViewModel
import com.bitso.challenge.viewmodels.TickersState
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Agustin Madina on 23/04/2021.
 */
class MainFragment : Fragment(R.layout.fragment_main) {
    private val tickersViewModel: TickersViewModel by viewModel()
    private val binding by viewBinding(FragmentMainBinding::bind)

    private lateinit var tickersAdapter: TickersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        tickersViewModel.getAllTickers()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        binding.tickersRecyclerView.layoutManager = layoutManager
        tickersAdapter = TickersAdapter(
            openMovieDetailsFn = { movie ->
                openTickerDetails(movie)
            }
        )
        binding.tickersRecyclerView.adapter = tickersAdapter
    }

    private fun openTickerDetails(ticker: Ticker) {
        val directions = MainFragmentDirections.showMovieDetail(ticker)
        findNavController().navigate(directions)
    }

    private fun setupObservers() {
        tickersViewModel.tickersState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is TickersState.Loading -> binding.loadingProgressBar.show()
                is TickersState.Success -> binding.loadingProgressBar.hide()
                is TickersState.Error -> {
                    binding.loadingProgressBar.hide()
                    val message = state.exception.getIOErrorMessage(requireContext())
                    requireContext().showToast(message)
                }
            }
        }

        tickersViewModel.tickers.observe(viewLifecycleOwner) { movies ->
            tickersAdapter.submitList(movies)
            tickersAdapter.notifyDataSetChanged()
        }
    }
}