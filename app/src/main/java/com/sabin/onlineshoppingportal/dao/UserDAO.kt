package com.sabin.onlineshoppingportal.dao

import androidx.room.Insert
import com.sabin.onlineshoppingportal.adapter.User

interface UserDAO{
    @Insert
    suspend fun registerUser(user : User)
}