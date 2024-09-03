package com.example.apis.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.apis.data.model.Category
import com.example.apis.view.viewmodel.NewsViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apis.data.model.Article
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun NewsScreen(navController: NavHostController, newsViewModel: NewsViewModel = hiltViewModel()) {
    val categories = listOf(
        Category("business"),
        Category("entertainment"),
        Category("general"),
        Category("health"),
        Category("science"),
        Category("sports"),
        Category("technology")
    )

    val selectedCategory by newsViewModel.selectedCategory.collectAsState()
    val newsArticles by newsViewModel.newsResponse.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        LazyRow(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            items(categories) { category ->
                CategoryChip(
                    category = category,
                    isSelected = category.name == selectedCategory,
                    onClick = { newsViewModel.selectCategory(category.name) }
                )
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(newsArticles) { article ->
                ArticleItem(
                    article = article,
                    onClick = {
                        val encodedTitle = encodeParam(article.title)
                        val encodedDescription = article.description?.let { encodeParam(it) }
                        val encodedAuthor = article.author?.let { encodeParam(it) }
                        val encodedUrl = encodeParam(article.url)
                        val encodedUrlToImage = article.urlToImage?.let { encodeParam(it) }

                        navController.navigate("article_details/$encodedTitle/$encodedDescription/$encodedAuthor/$encodedUrl/$encodedUrlToImage")
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryChip(category: Category, isSelected: Boolean, onClick: () -> Unit) {
    Chip(
        modifier = Modifier.padding(4.dp),
        onClick = onClick,
        colors = ChipDefaults.chipColors(
            contentColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface
        )
    ) {
        BasicText(text = category.name)
    }
}

@Composable
fun ArticleItem(article: Article, onClick: () -> Unit) {
    Text(
        text = article.title,
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    )
}

fun encodeParam(param: String): String {
    return URLEncoder.encode(param, StandardCharsets.UTF_8.toString())
}
