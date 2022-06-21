package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.MyPostBinding
import ru.netology.nmedia.post.Post


class PostsAdapter(
    private val onLikeClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MyPostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onLikeClicked, onShareClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class ViewHolder(
        private val binding: MyPostBinding,
        private val onLikeClicked: (Post) -> Unit,
        private val onShareClicked: (Post) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.likeButton.setOnClickListener {
                onLikeClicked(post)
            }
            binding.reply.setOnClickListener { onShareClicked(post) }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                authorName.text = post.author
                date.text = post.published
                textContent.text = post.content
                countLikes.text = smartCount(post.likes)
                countReply.text = smartCount(post.share)
                countComments.text = smartCount(post.countComment)
                countEyes.text = smartCount(post.countEyesPost)

                likeButton.setImageResource(
                    if (post.likedByMe) {
                        R.drawable.ic_red_baseline_favorite_24
                    } else {
                        R.drawable.ic_baseline_favorite_border_24
                    }
                )
            }
        }
    }

    private object DiffCallBack : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}

private fun smartCount(count: Int): String {
    return when (count) {
        in 1..999 -> "$count"
        in 1_000..1_099 -> "${count / 1_000}K"
        in 1_100..9_999 -> "${count / 1_000}.${count / 100 % 10}K"
        in 10_000..999_999 -> "${count / 1_000}K"
        in 1_000_000..1_099_999 -> "${count / 1_000_000}M"
        in 1_100_000..99_999_999 -> "${count / 1_000_000}.${count / 100_000 % 10}M"
        else -> ""
    }
}