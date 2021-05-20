package com.bitso.challenge.ui

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bitso.challenge.R
import com.bitso.challenge.databinding.FragmentTickerDetailsBinding
import com.bitso.challenge.extensions.viewBinding
import org.koin.android.ext.android.bind

class TickerDetailFragment : Fragment(R.layout.fragment_ticker_details) {

    private val binding by viewBinding(FragmentTickerDetailsBinding::bind)
    private val args: TickerDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ticker = args.ticker

        binding.tickerTitle.text = ticker.displayBook
        binding.bidPrice.text = ticker.bid
        binding.askPrice.text = ticker.ask
        binding.dayHighPrice.text = ticker.high
        binding.dayLowPrice.text = ticker.low
        binding.change24.text = ticker.change24
        binding.spreadPrice.text = ticker.spread
        binding.loadingProgressBar.visibility = GONE
    }
}