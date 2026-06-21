package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositorySQLiteImpl

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )
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
        clearDraft()
    }

    fun editById(post: Post) {
        edited.value = post
    }

    fun viewById(post: Post) {
        edited.value = post
    }

    //Save post

    private val _draft = MutableLiveData("")
    val draft: LiveData<String> = _draft

    fun changeDraft(content: String) {
        _draft.value = content
    }

    fun clearDraft() {
        _draft.value = ""
    }

}
