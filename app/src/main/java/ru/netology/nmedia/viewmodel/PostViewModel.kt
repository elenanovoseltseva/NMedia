package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryImpl()
    private val emptyPost = Post()
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
