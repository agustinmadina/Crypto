package com.bitso.challenge.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bitso.challenge.R
import com.bitso.challenge.databinding.FragmentTickerDetailsBinding
import com.bitso.challenge.extensions.viewBinding

class TickerDetailFragment : Fragment(R.layout.fragment_ticker_details) {
    //    private val movieViewModel: TickersViewModel by viewModel()
    private val binding by viewBinding(FragmentTickerDetailsBinding::bind)
    private val args: TickerDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ticker = args.ticker
    }
}