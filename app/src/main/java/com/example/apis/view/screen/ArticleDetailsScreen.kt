package com.example.apis.view.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun ArticleDetailsScreen(
    title: String,
    description: String,
    author: String,
    url: String,
    urlToImage: String
) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    // Decode URLs if necessary
    val decodedUrl = remember { URLDecoder.decode(url, StandardCharsets.UTF_8.name()) }
    val decodedUrlToImage = remember { URLDecoder.decode(urlToImage, StandardCharsets.UTF_8.name()) }
    val decodedAuthor = remember { URLDecoder.decode(author, StandardCharsets.UTF_8.name()) }
    val decodedTitle = remember { URLDecoder.decode(title, StandardCharsets.UTF_8.name()) }
    val decodedDescription = remember { URLDecoder.decode(description, StandardCharsets.UTF_8.name()) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Image
        val painter = rememberImagePainter(decodedUrlToImage)
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 8.dp)
        )

        // Author
        Text(text = "Author: $decodedAuthor", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        // Title
        Text(text = decodedTitle, style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        // Description
        Text(text = decodedDescription, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            // Share Button
            IconButton(onClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, decodedUrl)
                }
                context.startActivity(Intent.createChooser(intent, "Share Article"))
            }) {
                Icon(Icons.Default.Share, contentDescription = "Share Article")
            }

            // Open in Browser Button
            IconButton(onClick = {
                uriHandler.openUri(decodedUrl)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Open in Browser")
            }
        }
    }
}