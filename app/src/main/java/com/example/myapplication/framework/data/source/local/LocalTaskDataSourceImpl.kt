package com.example.myapplication.framework.data.source.local

import com.example.myapplication.data.model.local.TaskDTO
import com.example.myapplication.data.source.local.LocalTaskDataSource
import com.example.myapplication.domain.util.Result
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

    override suspend fun addTaskToLocal(taskDTO: TaskDTO): Result<TaskDTO> {
        return try {
            val data: TaskDTO? = realm?.writeBlocking {
                copyToRealm(taskDTO)
            }
            Result.Success(data as TaskDTO)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getTaskListFromLocal(): Result<List<TaskDTO>> {
        return try {
            val data: List<TaskDTO>? = realm?.copyFromRealm(
                realm!!.query<TaskDTO>().find()
            )
            Result.Success(data as List<TaskDTO>)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}
