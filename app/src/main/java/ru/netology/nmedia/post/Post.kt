package ru.netology.nmedia.post

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 888,
    val likedByMe: Boolean = false,
    val share: Int = 993,
    val sharedByMe: Boolean = true,

    val countComment: Int = 1_233,
    val countEyesPost: Int = 12_333_000
)