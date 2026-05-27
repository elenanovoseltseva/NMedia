package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.functions.prnCount

typealias LikeListener = (Post) -> Unit

typealias ShareListener = (Post) -> Unit

class PostsAdapter(private val likeListener: LikeListener
                   ,private val shareListener: ShareListener) :
    ListAdapter<Post, PostViewHolder>(PostViewHolder.PostDiffCallback) {
    //var list: List<Post> = emptyList()
    //    set(value) {
    //        field = value
     //       notifyDataSetChanged()
     //   }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        val binding =
            CardPostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return PostViewHolder(binding,
            likeListener, shareListener
        )
    }

    override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int) {
        val post = getItem(position)
        viewHolder.bind(post)
    }
}

class PostViewHolder(private val binding: CardPostBinding,
                     private val likeListener: LikeListener
                     ,private val shareListener: ShareListener
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            content.text = post.content
            published.text = post.published

            likedTxt.text = prnCount(post.likes)
            sharedTxt.text = prnCount(post.shares)
            viewedTxt.text = prnCount(post.views)

            likedImg.setImageResource(
                if (post.likedByMe) R.drawable.icon_liked_red else R.drawable.icon_liked
            )

           likedImg.setOnClickListener {
                likeListener(post)
           }

           sharedImg.setOnClickListener {
                shareListener(post)
           }

        }
    }

    object PostDiffCallback : DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
    }
}
