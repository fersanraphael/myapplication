package com.example.myapplication.data.repository

import com.example.myapplication.data.model.network.TaskModel
import com.example.myapplication.data.model.network.toEntity
import com.example.myapplication.data.source.local.LocalTaskDataSource
import com.example.myapplication.data.source.network.NetworkTaskDataSource
import com.example.myapplication.domain.entity.TaskEntity
import com.example.myapplication.domain.util.Result
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class TaskRepositoryImplTest {

    private lateinit var localTaskDataSource: LocalTaskDataSource
    private lateinit var networkTaskDataSource: NetworkTaskDataSource
    private lateinit var taskRepositoryImpl: TaskRepositoryImpl

    @Before
    fun setUp() {
        localTaskDataSource = mockk()
        networkTaskDataSource = mockk()
        taskRepositoryImpl = TaskRepositoryImpl(
            localTaskDataSource,
            networkTaskDataSource
        )
    }

    @Test
    fun `should return success result when getTaskFromNetwork function is called`() {
        runTest {
            val taskModel = TaskModel(
                activity = "activity",
                key = "key",
                link = "link",
                type = "type",
                participants = 1,
                accessibility = .1F,
                price = .1F
            )

            /*
             * Arrange.
             */
            coEvery {
                networkTaskDataSource.getTaskFromNetwork()
            } returns Result.Success(taskModel)

            /*
             * Act.
             */
            val result: Result<TaskEntity> = taskRepositoryImpl.getTaskFromNetwork()

            /*
             * Assert
             */
            assertEquals(taskModel.toEntity(), (result as Result.Success).value)
        }
    }
}
