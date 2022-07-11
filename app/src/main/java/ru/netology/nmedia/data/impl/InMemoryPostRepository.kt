package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.post.Post

class InMemoryPostRepository : PostRepository {

    private var nextId = GENERATED_POSTS_AMOUNT.toLong()

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override val data = MutableLiveData(
        List(GENERATED_POSTS_AMOUNT) { index ->
            if (index == 1) {
                Post(
                    id = index + 1L,
                    author = "Netology",
                    content = "Content $index",
                    published = "21.05.2022",
                    likes = 999,
                    shareCount = 999_999,
                    video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
                )
            } else {
                Post(
                    id = index + 1L,
                    author = "Netology",
                    content = "Content $index",
                    published = "21.05.2022",
                    likes = 999,
                    shareCount = 999_999
                )
            }
        }

    )

    override fun like(postId: Long) {
        data.value = posts.map {
            val likedOrNotCount =
                if (!it.likedByMe) it.likes + 1 else it.likes - 1
            if (it.id != postId) it
            else it.copy(
                likedByMe = !it.likedByMe,
                likes = likedOrNotCount
            )
        }

    }

    override fun share(postId: Long) {
        data.value = posts.map {
            val countShare = it.shareCount + 1
            if (it.id != postId) it
            else it.copy(shareCount = countShare)
        }

    }

    override fun delete(postId: Long) {
        data.value = posts.filterNot { it.id == postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }


    private fun insert(post: Post) {
        data.value = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private companion object {
        const val GENERATED_POSTS_AMOUNT = 100
    }


}