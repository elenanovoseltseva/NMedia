package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import ru.netology.nmedia.repository.PostRepositorySharedPrefs

class PostViewModel (application: Application): AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySharedPrefs(application)
    val emptyPost = Post()
    val data = repository.getAll()
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)

    val edited = MutableLiveData(emptyPost)

    fun saveById(content: String) {
        edited.value?.let { post ->
            val trimmed: String = content.trim()
            if (trimmed != post.content) {
                repository.saveById(post.copy(content = trimmed))
            }
        }
        edited.value = emptyPost
    }

    fun editById(post: Post) {
        edited.value = post
    }
}
