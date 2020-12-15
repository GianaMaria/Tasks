package com.practice.tasks.di

import android.app.Application
import androidx.room.Room
import com.practice.tasks.data.TaskDao
import com.practice.tasks.data.TaskDatabase
import com.practice.tasks.viewModels.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object DependencyGraph {

    private val databaseModule by lazy {
        module {
            fun providerDatabase(application: Application, callback: TaskDatabase.Callback): TaskDatabase {
                return Room.databaseBuilder(application, TaskDatabase::class.java, "task_database.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build()
            }

            fun providerTaskDao(database: TaskDatabase): TaskDao {
                return database.taskDao()
            }

            single { providerDatabase(androidApplication(), get()) }
            single { providerTaskDao(get()) }

            fun providerApplicationScope(): CoroutineScope {
                return CoroutineScope(SupervisorJob())
            }
        }
    }

    private val viewModelModule by lazy {
        module {
            viewModel { TasksViewModel(get()) }
        }
    }

    val modules: List<Module> = listOf(databaseModule, viewModelModule)
}