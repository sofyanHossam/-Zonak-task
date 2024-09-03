package com.example.apis.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.apis.data.model.Article
import androidx.room.OnConflictStrategy

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertArticles(articles: List<Article>)

    @Query("SELECT * FROM articles")
     fun getAllArticles(): List<Article>
}

