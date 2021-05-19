package com.bitso.sample.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bitso.sample.R
import com.bitso.sample.databinding.FragmentMovieDetailsBinding
import com.bitso.sample.extensions.getIOErrorMessage
import com.bitso.sample.extensions.showToast
import com.bitso.sample.extensions.viewBinding
import com.bitso.sample.viewmodels.MainViewModel
import com.bitso.sample.viewmodels.MovieState
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Agustin Madina on 23/04/2021.
 */
class MovieDetailFragment : Fragment(R.layout.fragment_movie_details) {
    private val movieViewModel: MainViewModel by viewModel()
    private val binding by viewBinding(FragmentMovieDetailsBinding::bind)
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        movieViewModel.getMovieDetail(args.movie.id.toInt())
    }

    private fun setupObservers() {
        movieViewModel.moviesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieState.Loading -> binding.loadingProgressBar.show()
                is MovieState.Success -> binding.loadingProgressBar.hide()
                is MovieState.Error -> {
                    binding.loadingProgressBar.hide()
                    val message = state.exception.getIOErrorMessage(requireContext())
                    requireContext().showToast(message)
                }
            }
        }

        movieViewModel.movieDetail.observe(viewLifecycleOwner) { movieDetail ->
           binding.movieDetailImdbRating.text = movieDetail.imdbID
        }
    }
}