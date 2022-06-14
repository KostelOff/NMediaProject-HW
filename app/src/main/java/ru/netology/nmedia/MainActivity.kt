package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.MyPostBinding
import ru.netology.nmedia.post.Post

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = MyPostBinding.inflate(layoutInflater) // раздувание на рабочий kt (MH)
        setContentView(binding.root) // функция для просмотра разработчиком рабочего kt (MH)

        val post = Post(
            0L, "Нетология. Университет интернет-профессий будущего",
            "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            "14.06.2022 г. в 14:00"
        )
        with(binding) {
            authorName.text = post.author
            date.text = post.published
            textContent.text = post.content
            if (post.likedByMe) {
                binding.likes.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
            countLikes.text = smartCount(post.likes)
            countReply.text = smartCount(post.share)
            countComments.text = smartCount(post.countComment)
            countEyes.text = smartCount(post.countEyesPost)

            binding.likes.setOnClickListener {
                post.likedByMe = !post.likedByMe
                binding.likes.setImageResource(
                    if (post.likedByMe) {
                        R.drawable.ic_red_baseline_favorite_24
                    } else {
                        R.drawable.ic_baseline_favorite_border_24
                    }
                )
                if (post.likedByMe) {
                    post.likes++
                } else {
                    post.likes--
                }
                countLikes.text = smartCount(post.likes)
            }
            binding.reply.setOnClickListener {
                post.sharedByMe = !post.sharedByMe
                if (post.sharedByMe) {
                    post.share++
                    countReply.text = smartCount(post.share)
                }
            }
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