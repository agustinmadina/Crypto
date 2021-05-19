package com.bitso.challenge.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitso.challenge.R
import com.bitso.challenge.adapters.MoviesAdapter
import com.bitso.challenge.databinding.FragmentMainBinding
import com.bitso.challenge.extensions.getIOErrorMessage
import com.bitso.challenge.extensions.showToast
import com.bitso.challenge.extensions.viewBinding
import com.bitso.challenge.network.models.Movie
import com.bitso.challenge.viewmodels.MainViewModel
import com.bitso.challenge.viewmodels.MovieState
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Agustin Madina on 23/04/2021.
 */
class MainFragment : Fragment(R.layout.fragment_main), AdapterView.OnItemSelectedListener {
    private val movieViewModel: MainViewModel by viewModel()
    private val binding by viewBinding(FragmentMainBinding::bind)

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoriesSpinner()
        setupRecyclerView()
        setupObservers()
        movieViewModel.getTopRatedMovies()

        binding.searchIcon.setOnClickListener {
            movieViewModel.searchMovie(binding.searchEditText.text.toString())
        }
    }

    private fun setupCategoriesSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.movie_categories_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        binding.spinner.onItemSelectedListener = this
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        binding.moviesRecyclerView.layoutManager = layoutManager
        moviesAdapter = MoviesAdapter(
            openMovieDetailsFn = { movie ->
                openMovie(movie)
            }
        )
        binding.moviesRecyclerView.adapter = moviesAdapter
    }

    private fun openMovie(movie: Movie) {
        val directions = MainFragmentDirections.showMovieDetail(movie)
        findNavController().navigate(directions)
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

        movieViewModel.movies.observe(viewLifecycleOwner) { movies ->
            moviesAdapter.submitList(movies)
            moviesAdapter.notifyDataSetChanged()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        movieViewModel.loadCategory(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}