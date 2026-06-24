package ru.netology.nmedia.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "author")
    val author: String = "",
    @ColumnInfo(name = "content")
    val content: String = "",
    @ColumnInfo(name = "published")
    val published: String = "",
    @ColumnInfo(name = "likes")
    val likes: Int = 0,
    @ColumnInfo(name = "shares")
    val shares: Int = 0,
    @ColumnInfo(name = "views")
    val views: Int = 0,
    @ColumnInfo(name = "video")
    val video: String = "",
    @ColumnInfo(name = "likedByMe")
    val likedByMe: Boolean = false
){
    fun toDto(): Post = Post(
        id = id,
        author = author,
        content = content,
        published = published,
        likes = likes,
        shares = shares,
        views = views,
        video = video,
        likedByMe = likedByMe
    )

    companion object {
        fun fromDto(dto: Post): PostEntity = with(dto){
            PostEntity(
                id = id,
                author = author,
                content = content,
                published = published,
                likes = likes,
                shares = shares,
                views = views,
                video = video,
                likedByMe = likedByMe
            )
        }
    }
}
