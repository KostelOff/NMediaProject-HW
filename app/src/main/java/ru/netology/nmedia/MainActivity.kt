package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.databinding.MyPostBinding
import ru.netology.nmedia.post.Post
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = MyPostBinding.inflate(layoutInflater) // раздувание на рабочий kt (MH)
        setContentView(binding.root) // функция для просмотра разработчиком рабочего kt (MH)

        val viewModel by viewModels<PostViewModel>()

        viewModel.data.observe(this) { post ->
            with(binding) {
                authorName.text = post.author
                date.text = post.published
                textContent.text = post.content
                countLikes.text = smartCount(post.likes)
                countReply.text = smartCount(post.share)
                countComments.text = smartCount(post.countComment)
                countEyes.text = smartCount(post.countEyesPost)

                binding.likeButton.setImageResource(
                    if (post.likedByMe) {
                        R.drawable.ic_red_baseline_favorite_24
                    } else {
                        R.drawable.ic_baseline_favorite_border_24
                    }
                )
            }
        }
        binding.reply.setOnClickListener {
            viewModel.onSharedClicked()
        }
        binding.likeButton.setOnClickListener {
            viewModel.onLikeClicked()
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
}
