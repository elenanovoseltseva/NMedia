package ru.netology.nmedia.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.sharePost
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {
    private val viewModel: PostViewModel by activityViewModels() //by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = FragmentFeedBinding.inflate(inflater,
            container,
            false)

        val adapter = PostsAdapter(
            object : PostListener {

                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShare(post: Post) {
                    viewModel.shareById(post.id)
                    sharePost(requireContext(), post)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onSave(post: Post) {
                    viewModel.saveById(post.content)
                }

                override fun onEdit(post: Post) {
                    viewModel.viewById(post)
                    findNavController().navigate(
                        R.id.action_feedFragment_to_newPostFragment,
                        Bundle().apply {
                            putString("content", post.content)
                        }
                    )
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

                override fun onView(post: Post) {
                    viewModel.viewById(post)
                    findNavController().navigate(
                        R.id.action_feedFragment_to_viewPostFragment,
                        Bundle().apply {
                            putLong("postId", post.id)
                        }
                    )
                }
            }
        )

        binding.list.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)

            if (posts.size > adapter.currentList.size) { // Применяем если размер списка увеличился
                binding.list.post { // Отложенное действие, чтобы не прокрутило до добавления нового элемента
                    binding.list.smoothScrollToPosition(0) // Скролл к верхней позиции
                }
            }
        }

        binding.addPost.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        return binding.root
    }
}
