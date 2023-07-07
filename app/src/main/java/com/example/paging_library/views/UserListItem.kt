package com.example.paging_library.views

import com.example.paging_library.model.users.User

data class UserListItem(
    val user: User,
    val inProgress: Boolean
){
    val id: Long get() = user.id
    val imageUrl: String get() = user.imageUrl
    val name: String get() = user.name
    val company: String get() = user.company
    val isFavorite: Boolean get() = user.isFavorite
}
