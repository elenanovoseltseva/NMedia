package ru.netology.nmedia

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left + v.paddingLeft,
                systemBars.top + v.paddingTop,
                systemBars.right + v.paddingRight,
                systemBars.bottom + v.paddingBottom
            )
            insets
        }

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likes = 17_399_999,
            shares = 910_999,
            views = 120,
            likedByMe = false
        )

        with(binding){
            author.text = post.author
            content.text = post.content
            published.text = post.published
            likedTxt.text =  prnCount(post.likes)
            sharedTxt.text = prnCount(post.shares)
            viewedTxt.text = prnCount(post.views)

            likedImg.setImageResource(if (post.likedByMe) R.drawable.icon_liked_red else R.drawable.icon_liked)

            likedImg.setOnClickListener {
                if(post.likedByMe) post.likes -- else post.likes++

                post.likedByMe = !post.likedByMe

                likedImg.setImageResource(if (post.likedByMe) R.drawable.icon_liked_red else R.drawable.icon_liked)

                likedTxt.text =  prnCount(post.likes)
            }

            sharedImg.setOnClickListener {
                post.shares++
                sharedTxt.text = prnCount(post.shares)
            }
        }
    }

    fun prnCount(count: Int): String{

        var strCount: String

        when{
            count < 1000 ->{
                strCount = count.toString()
            }
            count < 10_000 -> {
                val truncated = ((count / 1_000.0) * 10).toInt() / 10.0
                strCount = "${truncated}K"
            }
            count < 1_000_000->{
                strCount = String.format("%dK",  count / 1_000)
            }
            else ->{
                val truncated = ((count / 1_000_000.0) * 10).toInt() / 10.0
                strCount = "${truncated}M"
            }
        }
        return strCount
    }
}