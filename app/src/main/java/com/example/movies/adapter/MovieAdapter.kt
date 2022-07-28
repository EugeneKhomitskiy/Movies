package com.example.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.movies.databinding.CardMovieBinding
import com.example.movies.dto.Movie

interface OnInteractionListener {
    fun onOpenLink(movie: Movie)
}

class MovieAdapter(private val onInteractionListener: OnInteractionListener) :
    PagingDataAdapter<Movie, MovieViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = CardMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }
    }
}

class MovieViewHolder(
    private val binding: CardMovieBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        with(binding) {
            title.text = movie.displayTitle
            description.text = movie.summaryShort

            link.setOnClickListener {
                onInteractionListener.onOpenLink(movie)
            }

            Glide.with(image)
                .load(movie.multimedia.src)
                .transform(CenterInside(),RoundedCorners(24))
                .into(image)
        }
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.displayTitle == newItem.displayTitle
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}