package com.example.paging_library.views.model.users.repositirues.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.paging_library.views.model.users.User




@Entity(
    tableName = "users",
    indices = [
        Index("name")
    ]
)


data class UserDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val name: String,
    val company: String,
    val imageUrl: String,
    val isFavorite: Boolean
) {
    fun toUser(): User = User(
        id = id,
        imageUrl = imageUrl,
        company=company,
        name = name,
        isFavorite = isFavorite
    )
}


