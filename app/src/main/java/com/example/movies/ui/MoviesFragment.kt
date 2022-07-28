package com.example.movies.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.movies.R
import com.example.movies.adapter.MovieAdapter
import com.example.movies.adapter.MovieLoadStateAdapter
import com.example.movies.adapter.OnInteractionListener
import com.example.movies.databinding.FragmentMoviesBinding
import com.example.movies.dto.Movie
import com.example.movies.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val movieViewModel: MovieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentMoviesBinding.inflate(
            layoutInflater,
            container,
            false
        )

        val adapter = MovieAdapter(object : OnInteractionListener {
            override fun onOpenLink(movie: Movie) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.link.url))
                startActivity(browserIntent)
            }
        })

        binding.list.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter {
                adapter.retry()
            },
            footer = MovieLoadStateAdapter {
                adapter.retry()
            }
        )

        lifecycleScope.launchWhenCreated {
            movieViewModel.data.collectLatest(adapter::submitData)
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { state ->
                binding.swipeRefresh.isRefreshing =
                    state.refresh is LoadState.Loading
            }
        }

        binding.swipeRefresh.setOnRefreshListener(adapter::refresh)

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { state ->
                binding.progress.isVisible = state.refresh is LoadState.Loading
                if (state.refresh is LoadState.Error) {
                    Toast.makeText(context, R.string.error_loading, Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }
}