package com.example.myapplication.framework.di

import com.example.myapplication.data.repository.TaskRepositoryImpl
import com.example.myapplication.domain.repository.TaskRepository
import com.example.myapplication.domain.usecase.AddTaskToLocalUseCase
import com.example.myapplication.domain.usecase.GetTaskFromNetworkUseCase
import com.example.myapplication.domain.usecase.GetTaskListFromLocalUseCase
import com.example.myapplication.framework.MyApplicationRealm
import com.example.myapplication.framework.MyApplicationRetrofit
import com.example.myapplication.framework.data.source.local.LocalDataSource
import com.example.myapplication.framework.data.source.network.NetworkDataSource
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

    factory {
        LocalDataSource(
            myApplicationRealm = get()
        )
    }

    factory {
        NetworkDataSource(
            myApplicationRetrofit = get()
        )
    }

    factory<TaskRepository> {
        TaskRepositoryImpl(
            localDataSource = get(),
            networkDataSource = get()
        )
    }

    factory {
        AddTaskToLocalUseCase(
            taskRepository = get()
        )
    }

    factory {
        GetTaskFromNetworkUseCase(
            taskRepository = get()
        )
    }

    factory {
        GetTaskListFromLocalUseCase(
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
