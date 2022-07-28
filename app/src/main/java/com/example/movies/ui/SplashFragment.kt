package com.example.movies.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movies.R
import com.example.movies.databinding.FragmentSplashBinding
import com.example.movies.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val movieViewModel: MovieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentSplashBinding.inflate(
            layoutInflater,
            container,
            false
        )

        lifecycleScope.launchWhenCreated {
            movieViewModel.data.collectLatest {
                delay(1000)
                findNavController().navigate(R.id.action_splashFragment_to_moviesFragment)
            }
        }

        return binding.root
    }
}