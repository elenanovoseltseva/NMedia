package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryImpl : PostRepository {
    private var posts = listOf(
        Post(
            id = 7,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология!  Мой пост 7.",
            published = "24 мая в 18:36",
            likes = 14,
            shares = 4,
            views = 124,
            likedByMe = false
        ),
        Post(
            id = 6,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология!  Мой пост 6.",
            published = "23 мая в 18:36",
            likes = 13,
            shares = 3,
            views = 123,
            likedByMe = false
        ),
        Post(
            id = 5,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология!  Мой пост 5.",
            published = "22 мая в 18:36",
            likes = 500,
            shares = 912,
            views = 122,
            likedByMe = false
        ),
        Post(
            id = 4,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология!  Мой пост 4.",
            published = "24 мая в 18:36",
            likes = 14,
            shares = 4,
            views = 124,
            likedByMe = false
        ),
        Post(
            id = 3,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология!  Мой пост 3.",
            published = "23 мая в 18:36",
            likes = 13,
            shares = 3,
            views = 123,
            likedByMe = false
        ),
        Post(
            id = 2,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология!  Мой пост 2.",
            published = "22 мая в 18:36",
            likes = 12,
            shares = 912,
            views = 122,
            likedByMe = false
        ),
        Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likes = 17_399_999,
            shares = 910,
            views = 120,
            likedByMe = false
        )
    )

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it
            else it.copy(likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1)
        }

        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it
            else it.copy(shares =  it.shares + 1)
        }
        data.value = posts
    }
}