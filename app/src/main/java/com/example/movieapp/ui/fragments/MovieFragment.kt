package com.example.movieapp.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.movieapp.R
import com.example.movieapp.adapters.MovieLoadStateAdapter
import com.example.movieapp.adapters.MoviePagingAdapter
import com.example.movieapp.data.Movie
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie), MoviePagingAdapter.OnItemClickListener {
    private val viewModel by viewModels<MovieViewModel>()
    private var _binding : FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            _binding = FragmentMovieBinding.bind(view)

            val adapter = MoviePagingAdapter(this)

            binding.apply {
                rvMovie.setHasFixedSize(true)
                rvMovie.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = MovieLoadStateAdapter{adapter.retry()},
                    footer = MovieLoadStateAdapter{adapter.retry()}
                )
                btRetry.setOnClickListener {
                    adapter.retry()
                }
            }

        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
            adapter.addLoadStateListener { loadState ->
                binding.apply {
                    progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                    rvMovie.isVisible = loadState.source.refresh is LoadState.NotLoading
                    btRetry.isVisible = loadState.source.refresh is LoadState.Error
                    tvFailed.isVisible = loadState.source.refresh is LoadState.Error

                    if(loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        adapter.itemCount <1) {
                        rvMovie.isVisible = false
                        tvNotFound.isVisible = true
                    } else {
                        tvNotFound.isVisible = false
                    }
                }
            }

        setHasOptionsMenu(true)
    }

    override fun onItemClick(movie: Movie) {
        val action = MovieFragmentDirections.actionNavMovieToNavDetail(movie)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search,menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) {
                    binding.rvMovie.scrollToPosition(0)
                    viewModel.searchMovies(query)
                    searchView.clearFocus()
                }
                return  true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}