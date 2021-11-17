package com.example.movieapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.adapters.FavoriteMovieAdapter
import com.example.movieapp.data.Movie
import com.example.movieapp.databinding.FragmentFavoriteBinding
import com.example.movieapp.db.FavoriteMovie
import com.example.movieapp.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFavoriteBinding.bind(view)
        val adapter = FavoriteMovieAdapter()

        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.setMovieList(it)
            binding.apply {
                rvMovie.setHasFixedSize(true)
                rvMovie.adapter = adapter
            }
        }
        adapter.setOnItemClickCallBack(object : FavoriteMovieAdapter.OnItemClickCallBack {
            override fun onItemClick(favoriteMovie: FavoriteMovie) {
                val movie = Movie(
                    favoriteMovie.id_movie,
                    favoriteMovie.overview,
                    favoriteMovie.poster_path,
                    favoriteMovie.original_title
                )
                val action = FavoriteFragmentDirections.actionNavFavoriteToNavDetail(movie)
                findNavController().navigate(action)
            }
        })
    }

}