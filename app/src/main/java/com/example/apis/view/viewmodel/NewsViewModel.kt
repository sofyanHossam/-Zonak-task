package com.example.apis.view.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.apis.data.model.Article
import com.example.apis.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _selectedCategory = MutableStateFlow("business")
    val selectedCategory: StateFlow<String> = _selectedCategory

    private val _newsResponse = MutableStateFlow<List<Article>>(emptyList())
    val newsResponse: StateFlow<List<Article>> = _newsResponse

    init {
        fetchArticlesForCategory("business")
    }

    fun fetchArticlesForCategory(category: String) {
        viewModelScope.launch {
            if (isConnected()) {
                val response = newsRepository.getTopHeadlines(category, "us")
                _newsResponse.value = response
            } else {
                val cachedArticles = newsRepository.getCachedArticles()
                if (cachedArticles.isNotEmpty()) {
                    _newsResponse.value = cachedArticles
                } else {
                    Toast.makeText(getApplication(), "No internet and no cached data available", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        fetchArticlesForCategory(category)
    }

    private fun isConnected(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}



