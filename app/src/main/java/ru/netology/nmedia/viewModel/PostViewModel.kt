package ru.netology.nmedia.viewModel

import SingleLiveEvent
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.FilePostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.data.impl.SharedPrefsPostRepository
import ru.netology.nmedia.post.Post

class PostViewModel(
    application: Application
) : AndroidViewModel(application), PostInteractionListener {

    private val repository: PostRepository = FilePostRepository(application)

    val data by repository::data

    val sharedPostContent = SingleLiveEvent<String>()

    val navigateToPostContentScreenEvent = SingleLiveEvent<String>()

    val playVideo = SingleLiveEvent<String>()

    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Sasha",
            content = content,
            published = "Current date"
        )
        repository.save(post)
        currentPost.value = null
    }

    fun onAddClicked() {
        navigateToPostContentScreenEvent.call()
    }

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onShareClicked(post: Post) {
        sharedPostContent.value = post.content
        repository.share(post.id)
    }

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = post.content
    }

    override fun onPlayVideoClicked(post: Post) {
        val url: String = requireNotNull(post.video) {
            "Url must not be null"
        }
        playVideo.value = url
    }
}