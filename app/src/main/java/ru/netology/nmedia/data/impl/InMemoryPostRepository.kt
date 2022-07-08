package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.post.Post

class InMemoryPostRepository : PostRepository {

    private var nextId = GENERATES_POST_AMOUNT.toLong()

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
                "Контент № ${index + 1} : \n" +
                        "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. " +
                        "Затем появились курсы по дизайну, разработке, аналитике и управлению. " +
                        "Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. " +
                        "Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. " +
                        "Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                "14.06.2022 г. в 14:00",
                        video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
            )
        }
        data = MutableLiveData(initialPosts)
    }

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
            val countShare = it.share + 1
            if (it.id != postId) it
            else it.copy(share = countShare)
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
        const val GENERATES_POST_AMOUNT = 100
    }
}