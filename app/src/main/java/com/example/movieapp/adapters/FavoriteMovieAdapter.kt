package com.example.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.db.FavoriteMovie

class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>() {
    private lateinit var list : List<FavoriteMovie>

    private var onItemClickCallBack : OnItemClickCallBack ?= null

     fun setOnItemClickCallBack (onItemClickCallBack : OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }

    fun setMovieList(list: List<FavoriteMovie>){
        this.list = list
        notifyDataSetChanged()
    }

    inner class FavoriteMovieViewHolder(
        private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(favoriteMovie: FavoriteMovie) {
                with(binding) {
                Glide.with(itemView)
                    .load("${favoriteMovie.baseUrl}${favoriteMovie.poster_path}")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivMovie)

                    tvMovieTitle.text = favoriteMovie.original_title
                    binding.root.setOnClickListener{
                        onItemClickCallBack?.onItemClick(favoriteMovie)
                    }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallBack {
        fun  onItemClick(favoriteMovie: FavoriteMovie)
    }
}