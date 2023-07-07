package com.example.paging_library.model.users.repositirues.room

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.paging_library.model.users.User
import com.example.paging_library.model.users.UsersPageLoader
import com.example.paging_library.model.users.UsersPagingSource
import com.example.paging_library.model.users.repositirues.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class RoomUserRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val userDao: UsersDao
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
                prefetchDistance = PAGE_SIZE /2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UsersPagingSource(loader) }
        ).flow
    }

    override suspend fun setIsFavorite(user: User, isFavorite: Boolean) = withContext(ioDispatcher) {
        delay(1000)
        throwErrorsIfEnabled()

        val tuple = UpdateUserFavoriteFlagTuple(user.id, isFavorite)
        userDao.setIsFavorite(tuple)
    }

    override suspend fun delete(user: User)  = withContext(ioDispatcher){
        delay(1000)
        throwErrorsIfEnabled()

        userDao.delete(IdTuple(user.id))
    }


    private suspend fun getUsers (pageIndex: Int, pageSize: Int, searchBy: String): List<User> =
        withContext(ioDispatcher){

            delay(2000) //some delay to test laoding state
            throwErrorsIfEnabled()

            //calculate offset value required by DAO
            val offset = pageIndex * pageSize

            // get page
            val list = userDao.getUsers(pageSize, offset, searchBy)

            //map UserDbEntity to User
            return@withContext list
                .map (UserDbEntity::toUser)
        }

    private fun throwErrorsIfEnabled(){
        if (enableErrorsFlow.value) throw IllegalStateException("Error!")
    }

    private companion object{
        const val PAGE_SIZE = 40
    }
}