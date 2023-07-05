package com.example.paging_library.views.model.users.repositirues.room

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.paging_library.views.model.users.User
import com.example.paging_library.views.model.users.UsersPageLoader
import com.example.paging_library.views.model.users.UsersPagingSource
import com.example.paging_library.views.model.users.repositirues.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class RoomUserRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val userDao: UserDao
): UsersRepository {

    private val enableErrorsFlow = MutableStateFlow(false)


    override fun isErrorEnabled(): Flow<Boolean> = enableErrorsFlow

    override fun setErrorsEnabled(value: Boolean) {
        enableErrorsFlow.value = value
    }

    override fun getPageUsers(searchBy: String): Flow<PagingData<User>> {
        val loader: UsersPageLoader = { pageIndex, pageSize ->
            getUsers(pageIndex, pageSize, searchBy)
        }
        return Pager(
            config = PagingConfig(
                //for now let's use the same page size for initial
            // and subsequence loads
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE/2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {UsersPagingSource(loader)}
        ).flow
    }

    override suspend fun setIsFavorite(user: User, isFavorite: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(user: User) {
        TODO("Not yet implemented")
    }


    private suspend fun getUsers (pageIndex: Int, pageSize: Int, searchBy: String): List<User> =
        withContext(ioDispatcher){
            TODO("Not yet implemented")
        }

    private companion object{
        const val PAGE_SIZE = 40
    }
}