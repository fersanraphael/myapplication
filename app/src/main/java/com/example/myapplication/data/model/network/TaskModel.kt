package com.example.myapplication.data.model.network

import com.example.myapplication.domain.entity.TaskEntity
import com.google.gson.annotations.SerializedName

/**
 * @author Raphael Fersan
 */

internal data class TaskModel constructor(
    @SerializedName("activity")
    val activity: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("participants")
    val participants: Int,
    @SerializedName("accessibility")
    val accessibility: Float,
    @SerializedName("price")
    val price: Float
)

internal fun TaskModel.toEntity(): TaskEntity {
    return TaskEntity(
        activity = activity,
        key = key,
        link = link,
        type = type,
        participants = participants,
        accessibility = accessibility,
        price = price,
    )
}
