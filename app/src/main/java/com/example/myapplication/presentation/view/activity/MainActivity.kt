package com.example.myapplication.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.presentation.state.LocalTaskUiState
import com.example.myapplication.presentation.state.NetworkTaskUiState
import com.example.myapplication.presentation.state.TaskUiState
import com.example.myapplication.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Raphael Fersan
 */
internal class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        activityMainBinding.lifecycleOwner = this@MainActivity
        activityMainBinding.mainViewModel = mainViewModel

        registerStateObserver().also {
            mainViewModel.getTaskFromNetwork()
        }
    }

    private fun registerStateObserver() {
        lifecycleScope.launch {
            mainViewModel.taskUiStateFlow.collectLatest {
                when (it) {
                    is LocalTaskUiState.AddTaskSuccess -> mainViewModel.getTaskListFromLocal()
                    is LocalTaskUiState.GetTaskListSuccess -> activityMainBinding.myApplicationTextView.text = it.taskEntityList.toString()
                    is NetworkTaskUiState.GetTaskSuccess -> mainViewModel.addTaskToLocal(it.taskEntity)
                    is TaskUiState.Failure -> activityMainBinding.myApplicationTextView.text = it.throwable?.toString()
                    else -> {}
                }
            }
        }
    }
}
