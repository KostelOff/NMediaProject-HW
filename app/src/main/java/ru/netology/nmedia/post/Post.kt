package ru.netology.nmedia.post

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 999,
    var likedByMe: Boolean = false,
    var share: Int = 1_199_999,
    var sharedByMe: Boolean = false,

    val countComment: Int = 1_233,
    var countEyesPost: Int = 12_333_000
)