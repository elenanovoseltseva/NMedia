package ru.netology.nmedia.util

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.PopupMenu
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

fun prnCount(count: Int): String =
    when {
        count < 1_000 -> count.toString()
        count < 10_000 -> "${((count / 100.0).toInt() / 10.0)}K"
        count < 1_000_000 -> "${count / 1_000}K"
        else -> "${((count / 100_000.0).toInt() / 10.0)}M"
    }

fun bindPost(binding: CardPostBinding, post: Post) {
    with(binding) {

        author.text = post.author
        content.text = post.content
        published.text = post.published

        videoGroup.visibility =
            if (post.video.isBlank()) View.GONE else View.VISIBLE

        likedImg.isChecked = post.likedByMe
        likedImg.text = prnCount(post.likes)
        sharedImg.text = prnCount(post.shares)
    }
}

fun sharePost(context: Context, post: Post) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, post.content)
    }
    val chooser = Intent.createChooser(
        intent,
        context.getString(R.string.chooser_share_post)
    )
    context.startActivity(chooser)
}
