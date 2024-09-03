package com.example.apis.data.repository


import com.example.apis.data.network.NewsApiService
import com.example.apis.data.model.Article
import com.example.apis.data.local.ArticleDao
import javax.inject.Inject

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository @Inject constructor(
    private val apiService: NewsApiService,
    private val articleDao: ArticleDao
) {
    suspend fun getTopHeadlines(category: String, countryCode: String): List<Article> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getTopHeadlines(category, countryCode)
                if (response.articles.isNotEmpty()) {
                    articleDao.insertArticles(response.articles)
                }
                response.articles
            } catch (e: Exception) {
                // Return cached articles if there is an error (e.g., no internet)
                articleDao.getAllArticles()
            }
        }
    }

    suspend fun getCachedArticles(): List<Article> {
        return withContext(Dispatchers.IO) {
            articleDao.getAllArticles()
        }
    }
}




