package ru.netology.nmedia.dao

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Query
import androidx.room.Upsert
import androidx.room.Insert
import ru.netology.nmedia.entity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Upsert()
    fun saveById(post: PostEntity)

    @Query(value = """
        UPDATE posts SET
            likes = likes +  CASE WHEN likedByMe THEN -1 ELSE 1 END,
            likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
        WHERE id = :id;
    """)
    fun likeById(id: Long)

    @Query(value = "DELETE FROM posts WHERE id = :id")
    fun removeById(id: Long)

    @Query(value = "UPDATE posts SET shares = shares + 1 WHERE id = :id")
    fun shareById(id: Long)

    //fun editById(id: Long)
}