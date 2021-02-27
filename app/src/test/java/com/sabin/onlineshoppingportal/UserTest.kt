package com.sabin.onlineshoppingportal

import com.sabin.onlineshoppingportal.adapter.User
import com.sabin.onlineshoppingportal.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class UserTest {
        private lateinit var userRepository: UserRepository
        // -----------------------------User Testing-----------------------------
        @Test
        fun checkLogin() = runBlocking {
            userRepository = UserRepository()
            val response = userRepository.checkUser("sabin", "sabin")
            val expectedResult = true
            val actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
//        @Test
//        fun registerUser() = runBlocking {
//            val user =
//                User(fname = "test", lname = "test", username = "zxxcxcx", password = "testpassword")
//            userRepository = UserRepository()
//            val response = userRepository.registerUser(user)
//            val expectedResult = true
//            val actualResult = response.success
//            Assert.assertEquals(expectedResult, actualResult)
//        }
    }