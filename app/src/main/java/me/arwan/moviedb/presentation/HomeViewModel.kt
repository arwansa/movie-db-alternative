package me.arwan.moviedb.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.arwan.moviedb.core.Resource
import me.arwan.moviedb.core.launchSafeIO
import me.arwan.moviedb.data.model.Section
import me.arwan.moviedb.data.repository.MovieRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _result = MutableStateFlow<Resource<List<Section>>>(Resource.idle())
    val result = _result.asStateFlow()

    private var jobRequest: Job? = null

    fun search(searchKey: String, page: Int) {
        jobRequest = launchSafeIO(blockBefore = {
            _result.value = Resource.loading()
        }, blockIO = {
            val resource = movieRepository.search(searchKey, page)
            val movieList = resource.data?.movieList.orEmpty()
            _result.value = if (movieList.isNotEmpty()) {
                val section = listOf(
                    Section("Latest Movies", movieList, true),
                    Section("Latest Series", movieList, true),
                    Section("Trending Today", movieList, false)
                )
                Resource.success(section)
            } else {
                val message = resource.data?.error ?: resource.message.orEmpty()
                Resource.error(message)

            }
        }, blockException = {
            _result.value = Resource.error(it.localizedMessage.orEmpty())
        })
    }

    override fun onCleared() {
        jobRequest?.cancel()
        _result.value = Resource.idle()
        super.onCleared()
    }
}