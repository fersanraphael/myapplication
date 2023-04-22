package com.example.myapplication.data.model.local

import com.example.myapplication.domain.entity.TaskEntity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

/**
 * @author Raphael Fersan
 */

internal class TaskDTO : RealmObject {

    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var activity: String = ""
    var key: String = ""
    var link: String = ""
    var type: String = ""
    var participants: Int = 0
    var accessibility: Float = 0.0F
    var price: Float = 0.0F

    constructor()

    constructor(
        activity: String,
        key: String,
        link: String,
        type: String,
        participants: Int,
        accessibility: Float,
        price: Float
    ) {
        this@TaskDTO.activity = activity
        this@TaskDTO.key = key
        this@TaskDTO.link = link
        this@TaskDTO.type = type
        this@TaskDTO.participants = participants
        this@TaskDTO.accessibility = accessibility
        this@TaskDTO.price = price
    }
}

internal fun TaskDTO.toEntity(): TaskEntity {
    return TaskEntity(
        activity = activity,
        key = key,
        link = link,
        type = type,
        participants = participants,
        accessibility = accessibility,
        price = price
    )
}

internal fun TaskEntity.toDTO(): TaskDTO {
    return TaskDTO(
        activity = activity,
        key = key,
        link = link,
        type = type,
        participants = participants,
        accessibility = accessibility,
        price = price
    )
}
