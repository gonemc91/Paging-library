package com.example.paging_library.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paging_library.model.users.repositirues.room.UserDbEntity
import com.example.paging_library.model.users.repositirues.room.UsersDao

@Database(
    version = 1,
    entities = [
        UserDbEntity::class
    ]
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getUsersDao() : UsersDao
}