package com.sabin.ospwearos.response

data class LoginResponse (
    val success : Boolean? = null,
    val token : String? = null,
    val message : String? = null,
    val accountType: String? = null,
        )