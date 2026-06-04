package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
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

        val adapter = PostsAdapter(
            object : PostListener {

                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShare(post: Post) {
                    viewModel.shareById(post.id)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onSave(post: Post) {
                    viewModel.saveById(post.content)
                }

                override fun onEdit(post: Post) {
                    viewModel.editById(post)
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

        viewModel.edited.observe(this) { post ->
            if (post.id != 0L) {
                with(binding.content) {
                    setText(post.content)
                    AndroidUtils.showKeyboard(this)
                    binding.cancelEditGroup.visibility = View.VISIBLE
                }
            }
        }

        binding.savedImg.setOnClickListener {
            val content = binding.content.text?.toString()
            if (content.isNullOrBlank()) {
                Toast.makeText(this, R.string.err_text_empty, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.saveById(content)

            binding.content.clearFocus()
            binding.content.setText("")
            AndroidUtils.hideKeyboard(binding.content)
            binding.cancelEditGroup.visibility = View.GONE

        }

        binding.cancelEditImg.setOnClickListener {
            viewModel.edited.value = viewModel.emptyPost
            binding.content.clearFocus()
            binding.content.setText("")
            AndroidUtils.hideKeyboard(binding.content)
            binding.cancelEditGroup.visibility = View.GONE
        }


    }
}

