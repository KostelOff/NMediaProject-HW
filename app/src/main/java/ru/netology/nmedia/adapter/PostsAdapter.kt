package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.MyPostBinding
import ru.netology.nmedia.post.Post

internal class PostsAdapter(
    private val interactionListener: PostInteractionListener,
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MyPostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class ViewHolder(
        private val binding: MyPostBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.likeIcon.setOnClickListener { listener.onLikeClicked(post) }
            binding.shareIcon.setOnClickListener { listener.onShareClicked(post) }
            binding.options.setOnClickListener { popupMenu.show() }
            binding.videoBanner.setOnClickListener { listener.onPlayVideoClicked(post) }
            binding.playVideo.setOnClickListener { listener.onPlayVideoClicked(post) }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                authorName.text = post.author
                date.text = post.published
                textContent.text = post.content
                likeIcon.text = smartCount(post.likes)
                shareIcon.text = smartCount(post.share)
                countComments.text = smartCount(post.countComment)
                viewsCount.text = smartCount(post.countEyesPost)
                likeIcon.isChecked = post.likedByMe
                videoGroup.isVisible = post.video != null
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