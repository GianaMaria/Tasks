package com.practice.tasks

import androidx.multidex.MultiDexApplication
import com.practice.tasks.di.DependencyGraph
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TaskApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TaskApp)
            koin.loadModules(DependencyGraph.modules)
            koin.createRootScope()
        }
    }
}