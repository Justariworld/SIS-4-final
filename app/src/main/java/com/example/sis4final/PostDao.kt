package com.example.sis4final

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDao {

    @Query("SELECT * FROM posts")
    suspend fun getAllPosts(): List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>)

    @Query("DELETE FROM posts")
    suspend fun clearPosts()
}