package me.arwan.moviedb

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.arwan.moviedb.core.Resource
import me.arwan.moviedb.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: SectionAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        adapter = SectionAdapter()
        binding.recyclerView.adapter = adapter

        observeMovies()
        viewModel.search("Avengers Endgame", 1)
    }

    private fun observeMovies() = lifecycleScope.launch {
        viewModel.result.collect {
            val resultValue = viewModel.result.value
            when (resultValue.status) {
                Resource.Status.IDLE -> "Idle"
                Resource.Status.LOADING -> "Loading..."
                Resource.Status.SUCCESS -> {
                    adapter.setSectionList(resultValue.data.orEmpty())
                }

                else -> resultValue.message.orEmpty()
            }
        }
    }
}