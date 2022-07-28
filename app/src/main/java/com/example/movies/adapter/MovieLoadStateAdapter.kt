package com.example.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.LoadItemBinding

class MovieLoadStateAdapter(
    private val retryClickListener: () -> Unit
): LoadStateAdapter<LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(
            LoadItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            retryClickListener
        )
}

class LoadStateViewHolder(
    private val binding: LoadItemBinding,
    private val retryClickListener: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.apply {
            progress.isVisible = loadState is LoadState.Loading
            retry.isVisible = loadState is LoadState.Error
            retry.setOnClickListener {
                retryClickListener()
            }
        }
    }
}