package ru.netology.nmedia.post

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 999,
    val likedByMe: Boolean = false,
    val share: Int = 997,
    val countComment: Int = 1_233,
    val countEyesPost: Int = 12_333_000,
    val video: String? = null
)