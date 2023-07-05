package com.example.paging_library.views.model.users.repositirues

import com.example.paging_library.views.model.users.User
import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData

interface UsersRepository {

    /**
     * Whether errors are enabled or not. The value is listened by the bottom "Enabled Errors" checkbox
     * in the MainActivity.
     */

    fun isErrorEnabled(): Flow<Boolean>

    /**
     * Enable/disable errors when fetching users.
     */

    fun setErrorsEnabled(value: Boolean)

    /**
     *Get the paging list users.
     */
    fun getPageUsers(searchBy: String): Flow<PagingData<User>>


    /**
     * Set the "star" flag for the specified user.
     * @throws IllegalStateException if the 'Enable Errors' checkbox ic checked
     */

    suspend fun setIsFavorite(user: User, isFavorite: Boolean)

    /**
     * Delete the user item from the app.
     * @throws IllegalStateException if the 'Enable Errors' checkbox is checked.
     */
    suspend fun delete(user: User)


}