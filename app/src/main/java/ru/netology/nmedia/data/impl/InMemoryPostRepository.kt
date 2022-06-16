package ru.netology.nmedia.data.impl

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.databinding.MyPostBinding
import ru.netology.nmedia.post.Post

class InMemoryPostRepository : PostRepository {

    override val data = MutableLiveData(
        Post(
            0L, "Нетология. Университет интернет-профессий будущего",
            "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            "14.06.2022 г. в 14:00"
        )
    )

    override fun like() {
        val currentPost = data.value!!

        val likedPost = currentPost.copy(
            likedByMe = !currentPost.likedByMe  // 1
        )
        if (likedPost.likedByMe) {
            likedPost.likes++
        } else {
            likedPost.likes--
        }
        data.value = likedPost
    }

    override fun share() {
        val currentPost = data.value!!

        val sharedPost = currentPost.copy(
            sharedByMe = !currentPost.sharedByMe
        )
        if (sharedPost.sharedByMe) {
            sharedPost.share++
        }
        data.value = sharedPost
    }

//    override fun likeColor() {
//        val currentPost = data.value!!
//
//        val likedPost = currentPost.copy(
//            likedByMe = !currentPost.likedByMe
//        )
//        if (likedPost.likedByMe) {
//            R.drawable.ic_red_baseline_favorite_24
//        } else {
//            R.drawable.ic_baseline_favorite_border_24
//        }
//    }


}
