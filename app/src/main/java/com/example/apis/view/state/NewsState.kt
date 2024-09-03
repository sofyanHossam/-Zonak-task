package com.example.apis.view.state

import com.example.apis.data.model.Article

sealed class NewsState {
    object Loading : NewsState()
    data class Success(val articles: List<Article>) : NewsState()
    data class Error(val message: String) : NewsState()
}

