package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {


    override fun getAll(): LiveData<List<Post>> =  dao.getAll().map { postEntity ->
        postEntity.map {
            it.toDto()
        }
    }

    override fun saveById(post: Post) {
        dao.saveById(PostEntity.fromDto(post))
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
    }

    override fun editById(id: Long) {
       // posts = posts.filter { it.id != id }
       // data.value = posts
    }
}