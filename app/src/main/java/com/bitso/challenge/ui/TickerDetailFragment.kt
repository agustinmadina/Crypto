package com.bitso.challenge.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bitso.challenge.R
import com.bitso.challenge.databinding.FragmentTickerDetailsBinding
import com.bitso.challenge.extensions.viewBinding
import com.bitso.challenge.network.models.ChartEntry
import com.bitso.challenge.viewmodels.TickersViewModel
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_ticker_details.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TickerDetailFragment : Fragment(R.layout.fragment_ticker_details) {

    private val tickersViewModel: TickersViewModel by viewModel()
    private val binding by viewBinding(FragmentTickerDetailsBinding::bind)
    private val args: TickerDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        val ticker = args.ticker

        binding.tickerTitle.text = ticker.displayBook
        binding.bidPrice.text = ticker.bid
        binding.askPrice.text = ticker.ask
        binding.dayHighPrice.text = ticker.high
        binding.dayLowPrice.text = ticker.low
        binding.change24.text = ticker.change24
        binding.spreadPrice.text = ticker.spread
        binding.loadingProgressBar.visibility = GONE

        tickersViewModel.getTickerChartInfo(ticker.book, "1month")

        binding.chart1monthButton.setOnClickListener {
            tickersViewModel.getTickerChartInfo(ticker.book, "1month")
        }

        binding.chart3monthsButton.setOnClickListener {
            tickersViewModel.getTickerChartInfo(ticker.book, "3months")
        }

        binding.chart1yearButton.setOnClickListener {
            tickersViewModel.getTickerChartInfo(ticker.book, "1year")
        }
    }

    private fun setupObservers() {
//        tickersViewModel.tickersState.observe(viewLifecycleOwner) { state ->
//            when (state) {
//                is TickersState.Loading -> {
//                    binding.loadingProgressBar.show()
//                    binding.retryTextView.hide()
//                }
//                is TickersState.Success -> {
//                    binding.loadingProgressBar.hide()
//                    binding.retryTextView.hide()
//                    swipeRefreshLayout.isRefreshing = false
//                }
//                is TickersState.Error -> {
//                    binding.loadingProgressBar.hide()
//                    binding.retryTextView.show()
//                    swipeRefreshLayout.isRefreshing = false
//                    binding.retryTextView.setOnClickListener { tickersViewModel.getAllTickers() }
//                    val message = state.exception.getIOErrorMessage(requireContext())
//                    requireContext().showToast(message)
//                }
//            }
//        }

        tickersViewModel.tickerChartInfo.observe(viewLifecycleOwner) { entries ->
            setUpChart(entries)
        }
    }


    private fun setUpChart(entries: List<ChartEntry>) {
        with(binding.chartView) {
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            xAxis.isEnabled = false
            legend.isEnabled = false
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(false)
            setPinchZoom(false)
            setDrawMarkers(true)
            isHighlightPerTapEnabled = true
        }

        val data =
            entries.map {
                Entry(
                    it.timestamp.toFloat(),
                    it.value.toFloat()
                )
            }

        val dataSet = LineDataSet(data, "Unused label").apply {
            color = Color.GREEN
            valueTextColor = Color.GRAY
            highLightColor = Color.RED
            setDrawCircles(false)
            setDrawValues(false)
            lineWidth = 1.5f
            isHighlightEnabled = true
            setDrawHighlightIndicators(false)
        }
        binding.chartView.data = LineData(dataSet)
        binding.chartView.invalidate()
    }
}