package com.example.myapplication.domain.entity

/**
 * @author Raphael Fersan
 */
internal data class TaskEntity constructor(
    val activity: String,
    val key: String,
    val link: String,
    val type: String,
    val participants: Int,
    val accessibility: Float,
    val price: Float
)
