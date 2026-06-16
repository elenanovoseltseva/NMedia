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
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.databinding.FragmentViewPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.sharePost
import ru.netology.nmedia.viewmodel.PostViewModel

/*
class ViewPostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    private val postId by lazy {
        requireArguments().getLong("postId")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View
    {

        val binding = CardPostBinding.inflate(inflater, container, false)

        val post = viewModel.data.value?.find { it.id == postId }

        if (post != null) {
            bindPost(binding, post)
        }

        return binding.root
    }
}
*/
class ViewPostFragment : Fragment(R.layout.fragment_view_post) {

    val viewModel: PostViewModel by activityViewModels()


    val adapter = PostsAdapter(object : PostListener {

        override fun onView(post: Post) {}

        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }
        override fun onShare(post: Post) {
            viewModel.shareById(post.id)
            sharePost(requireContext(), post)
        }
        override fun onEdit(post: Post) {
            viewModel.editById(post)

            val content = post?.content ?: ""

            findNavController().navigate(
                R.id.action_viewPostFragment_to_newPostFragment,
                Bundle().apply {
                    putString("content", content)
                },
                NavOptions.Builder()
                    .setPopUpTo(R.id.viewPostFragment, true)
                    .build()
            )
        }
        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
            findNavController().navigateUp()

        }

        override fun onSave(post: Post) {}
        override fun onVideo(post: Post) {}
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // val binding = FragmentFeedBinding.bind(view)

       // val recyclerView = binding.list
       // recyclerView.adapter = adapter

        val binding = FragmentViewPostBinding.bind(view)

        binding.viewPost.adapter = adapter


        val postId = requireArguments().getLong("postId")

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == postId } ?: return@observe
            adapter.submitList(listOf(post))
        }

        binding.closeWin.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}

