package com.practice.tasks.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback(
        private val database: TaskDatabase,
        private val dao: TaskDao,
        private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            database.taskDao()

            applicationScope.launch {
                dao.insert(Task(name = "do something", important = true))
                dao.insert(Task("do something2", completed = true))
                dao.insert(Task("do something3"))
                dao.insert(Task("do something4", important = true))
                dao.insert(Task("do something5"))
                dao.insert(Task("do something6"))
            }
        }

    }
}