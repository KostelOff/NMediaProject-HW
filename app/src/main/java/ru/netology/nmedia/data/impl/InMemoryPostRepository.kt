package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.post.Post

class InMemoryPostRepository : PostRepository {
    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val initialPosts = List(100) { index ->
            Post(
                index + 1L, "Нетология. Университет интернет-профессий будущего",
                "Контент № ${index + 1}",
                "14.06.2022 г. в 14:00"
            )
        }
        data = MutableLiveData(initialPosts)
    }

    override fun like(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId) {
                post.copy(
                    likedByMe = !post.likedByMe,
                    likes = if (post.likedByMe) {
                        post.likes - 1
                    } else {
                        post.likes + 1
                    }
                )
            } else {
                post
            }
        }
        data.value = posts
    }

    override fun share(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId) {
                post.copy(share = post.share + 1)
            } else {
                post
            }
        }
        data.value = posts
    }
}