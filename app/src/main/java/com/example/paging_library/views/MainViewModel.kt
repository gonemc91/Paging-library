package com.example.paging_library.views

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.nav_components_2_tabs_exercise.R
import com.example.paging_library.MutableLiveEvent
import com.example.paging_library.adapters.UserAdapter
import com.example.paging_library.model.users.repositirues.UsersRepository
import com.example.paging_library.publishEvent
import com.example.paging_library.share
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class MainViewModel(
    private val usersRepository: UsersRepository
) : ViewModel(), UserAdapter.Listener {

    val isErrorsEnabled: Flow<Boolean> = usersRepository.isErrorEnabled()

    val usersFlow: Flow<PagingData<UserListItem>>
        get() {
            TODO()
        }

    private  val searchBy = MutableLiveData("")

    private val localChanges = LocalChanges()
    private val localChangesFlow = MutableStateFlow(OnChange(localChanges))

    private val _errorEvents = MutableLiveEvent<Int>()
    val errorEvents = _errorEvents.share()

    private val _scrollEvents = MutableLiveEvent<Unit>()
    val scrollEvents = _scrollEvents.share()

    private val _invalidateEvents = MutableLiveEvent<Unit>()
    val invalidateEvents = _invalidateEvents.share()

    init{


    }

    override fun onUserDelete(userListItem: UserListItem) {
        if (isInProgress(userListItem)) return
        viewModelScope.launch {
            try{
                setProgress(userListItem, true)
                delete(userListItem)
            }catch (e: Exception){
                showError(R.string.error_delete)
            }finally {
                setProgress(userListItem, false)
            }
        }
    }





    private fun setProgress(userListItem: UserListItem, inProgress: Boolean){
        if(inProgress){
            localChanges.idsInProgress.add(userListItem.id)
        }else{
            localChanges.idsInProgress.remove(userListItem.id)
        }
        localChangesFlow.value = OnChange(localChanges)
    }

    private fun isInProgress(userListItem: UserListItem) =
        localChanges.idsInProgress.contains(userListItem.id)

    private suspend fun delete(userListItem: UserListItem){
        usersRepository.delete(userListItem.user)
        invalidateList()
    }
    private fun invalidateList(){
        _invalidateEvents.publishEvent(Unit)
    }

    private fun showError(@StringRes errorMessage: Int){
        _errorEvents.publishEvent(errorMessage)
    }




    /**
     * Non-data class which passing the same reference to the
     * MutableStateFlow multiply times in a row.
     */
    class OnChange<T>(val value: T)

    /**
     * Contains:
     * 1) identifies of items are processed now (deleting or favorite
     * flag updating).
     * 2) Local favorite flag updates to avoid list reloading
     */

    class LocalChanges{
        val idsInProgress = mutableSetOf<Long>()
        val favoriteFlags = mutableMapOf<Long, Boolean>()
    }

}