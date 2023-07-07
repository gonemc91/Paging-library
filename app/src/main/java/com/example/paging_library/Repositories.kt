package com.example.paging_library

import android.content.Context
import androidx.room.Room
import com.example.paging_library.model.AppDatabase
import com.example.paging_library.model.users.User
import com.example.paging_library.model.users.repositirues.UsersRepository
import com.example.paging_library.model.users.repositirues.room.RoomUserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


/**
 *Contains singleton dependencies.
 */

object Repositories {

    private lateinit var applicationContext: Context

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "databse.db" )
            .createFromAsset("initial_database.db")
            .build()
    }

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    //----

    val usersRepository: UsersRepository by lazy {
        RoomUserRepository(ioDispatcher, database.getUsersDao())
    }

    /**
     * Call this method in all all applications components that may be created at app startup/restoring
     * (e.g. in onCreate of activities and services)
     */

    fun init(context: Context){
        applicationContext = context

    }
}