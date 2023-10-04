package me.arwan.moviedb.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.arwan.moviedb.R
import me.arwan.moviedb.core.Resource
import me.arwan.moviedb.core.setGone
import me.arwan.moviedb.core.setVisible
import me.arwan.moviedb.core.showToast
import me.arwan.moviedb.data.model.MovieResponse
import me.arwan.moviedb.data.model.Section
import me.arwan.moviedb.databinding.FragmentHomeBinding
import me.arwan.moviedb.presentation.HomeViewModel
import me.arwan.moviedb.ui.adapter.MovieItemCallback
import me.arwan.moviedb.ui.adapter.SectionAdapter

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: SectionAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        setupRecyclerView()
        observeMovies()

        viewModel.search("Avengers Endgame", 1)
    }

    private fun setupRecyclerView() {
        adapter = SectionAdapter(object : MovieItemCallback {
            override fun onProfileClicked() {
                context?.showToast("Profile clicked")
            }

            override fun onMovieItemClicked(movie: MovieResponse) {
                context?.showToast("Movie clicked: ${movie.title}")
            }

        })
        binding.recyclerView.adapter = adapter
    }

    private fun observeMovies() = lifecycleScope.launch {
        viewModel.result.collect {
            val resultValue = viewModel.result.value
            when (resultValue.status) {
                Resource.Status.IDLE -> {}
                Resource.Status.LOADING -> showLoadingState()
                Resource.Status.SUCCESS -> showSuccessState(resultValue.data.orEmpty())
                Resource.Status.ERROR -> showErrorState(resultValue.message.orEmpty())
            }
        }
    }

    private fun showLoadingState() {
        binding.progressBar.setVisible()
        binding.recyclerView.setGone()
        binding.textViewMessage.setGone()
        binding.buttonRetry.setGone()
    }

    private fun showSuccessState(data: List<Section>) {
        adapter.setSectionList(data)
        binding.recyclerView.setVisible()
        binding.progressBar.setGone()
        binding.textViewMessage.setGone()
        binding.buttonRetry.setGone()
    }

    private fun showErrorState(message: String) {
        binding.textViewMessage.text = message
        binding.textViewMessage.setVisible()
        binding.buttonRetry.setVisible()
        binding.progressBar.setGone()
        binding.recyclerView.setGone()
    }
}