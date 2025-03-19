package com.mek.tmdbapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mek.tmdbapp.Util.loadCircleImage
import com.mek.tmdbapp.databinding.ItemHomeRecyclerViewBinding
import com.mek.tmdbapp.model.MovieItems

interface MovieClickListener{
    fun onMovieClick(movieId: Int)
}

class MovieAdapter(private val movieList : List<MovieItems?>,private val movieClickListener: MovieClickListener) : RecyclerView.Adapter<MovieAdapter.viewHolder>() {

    inner class viewHolder(val binding: ItemHomeRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var binding = ItemHomeRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
       return movieList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val movie = movieList[position]

        holder.binding.textViewMovieTitle.text = movie?.title
        holder.binding.textViewMovieOverview.text = movie?.overview
        holder.binding.textViewMovieVote.text = movie?.voteAverage.toString()

        holder.binding.imageViewMovie.loadCircleImage(movie?.posterPath)

        holder.binding.root.setOnClickListener {
            movie?.id?.let {
                movieClickListener.onMovieClick(movieId = movie.id)
            }

        }
    }
}