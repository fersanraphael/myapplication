package com.example.myapplication.framework.data.source.local

import com.example.myapplication.data.model.local.TaskDTO
import com.example.myapplication.data.source.local.LocalTaskDataSource
import com.example.myapplication.framework.MyApplicationRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

/**
 * @author Raphael Fersan
 */
internal class LocalTaskDataSourceImpl constructor(
    private val myApplicationRealm: MyApplicationRealm
) : LocalTaskDataSource {

    private val realm: Realm? by lazy {
        myApplicationRealm.get()
    }

    override suspend fun addTaskToLocal(taskDTO: TaskDTO): TaskDTO? {
        return realm?.writeBlocking {
            copyToRealm(taskDTO)
        }
    }

    override suspend fun getTaskListFromLocal(): List<TaskDTO>? {
        return realm?.copyFromRealm(
            realm!!.query<TaskDTO>().find()
        )
    }
}
