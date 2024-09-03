package com.example.apis.view.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.apis.data.model.Article
import com.example.apis.data.model.Source
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor() : ViewModel() {
    // Define a state to hold the article details
    var article: Article? by mutableStateOf(null)
        private set

    // Method to set the article details
    fun setArticleDetails(
        title: String,
        description: String,
        author: String,
        url: String,
        urlToImage: String
    ) {
        article = Article(
            source = Source("","null"), // Set as needed or adjust Article model to handle null
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = "", // Set as needed
            content = "" // Set as needed
        )
    }
}
