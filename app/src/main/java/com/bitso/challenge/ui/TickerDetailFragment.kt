package com.bitso.challenge.ui

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bitso.challenge.R
import com.bitso.challenge.databinding.FragmentTickerDetailsBinding
import com.bitso.challenge.extensions.viewBinding

/**
 * Created by Agustin Madina on 23/04/2021.
 */
class TickerDetailFragment : Fragment(R.layout.fragment_ticker_details) {
    //    private val movieViewModel: TickersViewModel by viewModel()
    private val binding by viewBinding(FragmentTickerDetailsBinding::bind)
    private val args: TickerDetailFragmentArgs by navArgs()

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupObservers()
//        movieViewModel.getMovieDetail(args.movie.id.toInt())
//    }
//
//    private fun setupObservers() {
//        movieViewModel.tickersState.observe(viewLifecycleOwner) { state ->
//            when (state) {
//                is TickersState.Loading -> binding.loadingProgressBar.show()
//                is TickersState.Success -> binding.loadingProgressBar.hide()
//                is TickersState.Error -> {
//                    binding.loadingProgressBar.hide()
//                    val message = state.exception.getIOErrorMessage(requireContext())
//                    requireContext().showToast(message)
//                }
//            }
//        }
//
//        movieViewModel.movieDetail.observe(viewLifecycleOwner) { movieDetail ->
//           binding.movieDetailImdbRating.text = movieDetail.imdbID
//        }
//    }
}