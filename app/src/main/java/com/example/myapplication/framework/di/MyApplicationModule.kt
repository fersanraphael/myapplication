package com.example.myapplication.framework.di

import com.example.myapplication.data.repository.TaskRepositoryImpl
import com.example.myapplication.data.source.local.LocalTaskDataSource
import com.example.myapplication.data.source.network.NetworkTaskDataSource
import com.example.myapplication.domain.repository.TaskRepository
import com.example.myapplication.domain.usecase.*
import com.example.myapplication.framework.MyApplicationRealm
import com.example.myapplication.framework.MyApplicationRetrofit
import com.example.myapplication.framework.data.source.local.LocalTaskDataSourceImpl
import com.example.myapplication.framework.data.source.network.NetworkTaskDataSourceImpl
import com.example.myapplication.framework.util.schemes
import com.example.myapplication.presentation.viewmodel.MainViewModel
import io.realm.kotlin.RealmConfiguration
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * @author Raphael Fersan
 */
internal val myApplicationModule: Module = module {

    single {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .build()
        MyApplicationRetrofit(
            okHttpClient = okHttpClient
        )
    }

    single {
        val realmConfiguration: RealmConfiguration = RealmConfiguration.Builder(schemes)
            .build()
        MyApplicationRealm(
            realmConfiguration = realmConfiguration
        )
    }

    factory<LocalTaskDataSource> {
        LocalTaskDataSourceImpl(
            myApplicationRealm = get()
        )
    }

    factory<NetworkTaskDataSource> {
        NetworkTaskDataSourceImpl(
            myApplicationRetrofit = get()
        )
    }

    factory<TaskRepository> {
        TaskRepositoryImpl(
            localTaskDataSource = get(),
            networkTaskDataSource = get()
        )
    }

    factory<AddTaskToLocalUseCase> {
        AddTaskToLocalUseCaseImpl(
            taskRepository = get()
        )
    }

    factory<GetTaskFromNetworkUseCase> {
        GetTaskFromNetworkUseCaseImpl(
            taskRepository = get()
        )
    }

    factory<GetTaskListFromLocalUseCase> {
        GetTaskListFromLocalUseCaseImpl(
            taskRepository = get()
        )
    }

    viewModel {
        MainViewModel(
            addTaskToLocalUseCase = get(),
            getTaskFromNetworkUseCase = get(),
            getTaskListFromLocalUseCase = get(),
            myApplicationRealm = get()
        )
    }
}
