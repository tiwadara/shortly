package com.tiwa.movies.ui.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiwa.common.model.Movie
import com.tiwa.common.repository.MovieRepositoryImpl
import com.tiwa.common.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.ObservableProperty

@HiltViewModel
class MovieViewModel @Inject constructor( private val repository: MovieRepositoryImpl): ViewModel() {

    private var movieList: LiveData<List<Movie>> = repository.getMovieList()
    private var isLoading: LiveData<Boolean> = repository.isLoading()
    private val itemClicked = MutableLiveData<Int>()
    val initialMovieId = -1

    fun getMovies(): LiveData<List<Movie>> {
        viewModelScope.launch {
            val response = repository.loadMovies()
            if (response.status == Status.SUCCESS){
                viewModelScope.launch {
                    repository.saveNewMovies(response.data?.results)
                    repository.stopLoading()
                }

            }
        }
        return movieList
    }

    fun movieItemClicked(): LiveData<Int> {
        return  itemClicked
    }

    fun setItemClicked(): LiveData<Int> {
        itemClicked.value = initialMovieId
        return  itemClicked
    }

    fun loadMovie(movieId: Int) {
        itemClicked.postValue(movieId)
        repository.getMovie(movieId)
    }

    fun getIsLoading(): LiveData<Boolean> {
        return isLoading
    }
}