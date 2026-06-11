package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Для клавиатуры:
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val isImeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            v.setPadding(
                v.paddingLeft,
                systemBars.top,
                v.paddingRight,
                if (isImeVisible) imeInsets.bottom else systemBars.bottom
            )
            insets
        }

        val viewModel: PostViewModel by viewModels()

        val postContract = registerForActivityResult(NewPostContract) { result ->
            result ?: return@registerForActivityResult
            viewModel.saveById(result)
        }

        val adapter = PostsAdapter(
            object : PostListener {

                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShare(post: Post) {
                    viewModel.shareById(post.id)
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, post.content)
                    }

                    val chooser =
                        Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    startActivity(chooser)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onSave(post: Post) {
                    viewModel.saveById(post.content)
                }

                override fun onEdit(post: Post) {
                    viewModel.editById(post)
                    postContract.launch(post.content)
                }

                override fun onVideo(post: Post) {
                    if (post.video.isNullOrBlank()) return

                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video)).apply {
                        type = "video/*"
                    }

                    val chooser =
                        Intent.createChooser(intent,getString(R.string.chooser_view_video))
                    startActivity(chooser)
                }
            }
        )

        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)

            if (posts.size > adapter.currentList.size) { // Применяем если размер списка увеличился
                binding.list.post { // Отложенное действие, чтобы не прокрутило до добавления нового элемента
                    binding.list.smoothScrollToPosition(0) // Скролл к верхней позиции
                }
            }
        }

        binding.addPost.setOnClickListener {
            postContract.launch("")
        }
    }
}

