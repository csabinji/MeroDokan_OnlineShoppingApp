package com.sabin.onlineshoppingportal.dao

import androidx.room.Dao
import androidx.room.Insert
import com.sabin.onlineshoppingportal.adapter.User

@Dao
interface UserDAO{
    @Insert
    suspend fun registerUser(user : User)
}