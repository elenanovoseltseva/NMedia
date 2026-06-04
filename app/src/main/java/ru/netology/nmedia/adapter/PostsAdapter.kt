package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.functions.prnCount

typealias LikeListener = (Post) -> Unit

typealias ShareListener = (Post) -> Unit

typealias RemoveListener = (Post) -> Unit

interface PostListener{
    fun onRemove(post: Post)
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun onEdit(post: Post)
    fun onSave(post: Post)
}

class PostsAdapter(
    private val listener: PostListener
) :
    ListAdapter<Post, PostViewHolder>(PostViewHolder.PostDiffCallback) {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        val binding =
            CardPostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return PostViewHolder(binding, listener)
    }

    override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int) {
        val post = getItem(position)
        viewHolder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostListener
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            content.text = post.content
            published.text = post.published

            //likedTxt.text = prnCount(post.likes)
            //sharedTxt.text = prnCount(post.shares)
            //viewedTxt.text = prnCount(post.views)

           // likedImg.setImageResource(
           //     if (post.likedByMe) R.drawable.icon_liked_red else R.drawable.icon_liked
           // )

            likedImg.isChecked = post.likedByMe

            likedImg.text = prnCount(post.likes)
            sharedImg.text = prnCount(post.shares)

            likedImg.setOnClickListener {
                listener.onLike(post)
            }

            sharedImg.setOnClickListener {
                listener.onShare(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_post)

                    setOnMenuItemClickListener { item ->
                        when (item.itemId){
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

        }
    }

    object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
    }
}
