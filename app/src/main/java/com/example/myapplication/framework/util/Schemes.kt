package com.example.myapplication.framework.util

import com.example.myapplication.data.model.local.TaskDTO
import io.realm.kotlin.types.RealmObject
import kotlin.reflect.KClass

/**
 * @author Raphael Fersan
 */

internal val schemes: Set<KClass<out RealmObject>> = setOf(
    TaskDTO::class
)
