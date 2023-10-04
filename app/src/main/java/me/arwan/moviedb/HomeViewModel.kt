package me.arwan.moviedb

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
        jobRequest = launchSafeIO(
            blockBefore = {
                _result.value = Resource.loading()
            },
            blockIO = {
                val resource = movieRepository.search(searchKey, page)
                val error = resource.data?.error
                _result.value = if (error.orEmpty().isNotEmpty()) {
                    Resource.error(error.orEmpty())
                } else {
                    val movieList = resource.data?.movieList.orEmpty()
                    val section = listOf(
                        Section("Latest Movies", movieList, true),
                        Section("Latest Series", movieList, true),
                        Section("Trending Today", movieList, false)
                    )
                    Resource.success(section)
                }
            },
            blockException = {
                _result.value = Resource.error(it.localizedMessage.orEmpty())
            }
        )
    }

    override fun onCleared() {
        jobRequest?.cancel()
        _result.value = Resource.idle()
        super.onCleared()
    }
}