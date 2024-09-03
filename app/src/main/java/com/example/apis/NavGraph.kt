package com.example.apis

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.apis.view.screen.ArticleDetailsScreen
import com.example.apis.view.screen.NewsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "news") {
        composable("news") {
            NewsScreen(navController)
        }
        composable("article_details/{title}/{description}/{author}/{url}/{urlToImage}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""
            val author = backStackEntry.arguments?.getString("author") ?: ""
            val url = backStackEntry.arguments?.getString("url") ?: ""
            val urlToImage = backStackEntry.arguments?.getString("urlToImage") ?: ""

            ArticleDetailsScreen(
                title = title,
                description = description,
                author = author,
                url = url,
                urlToImage = urlToImage
            )
        }
    }
}

