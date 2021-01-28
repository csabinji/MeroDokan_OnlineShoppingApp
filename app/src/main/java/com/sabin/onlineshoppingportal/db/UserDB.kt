package com.sabin.roomdatabaseactivity.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sabin.onlineshoppingportal.adapter.User
import com.sabin.onlineshoppingportal.dao.UserDAO

@Database(
        entities = [(User::class)],
        version = 1
)
abstract class StudentDB :RoomDatabase() {
    abstract fun getUserDao() : UserDAO
    companion object{
        @Volatile
        private var instance : StudentDB? = null
        fun getInstance(context : Context): StudentDB{
            if(instance==null){
                synchronized(StudentDB::class){
                    instance = buildDatabase(context)
                }
            }
            return instance!!
        }
        private fun buildDatabase(context : Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        StudentDB::class.java,
                        "UserDB"
                ).build()
    }
}