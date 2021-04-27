package com.educards.nuts.demo.dto

data class DemoLoginResponseDTO(
    val firstName: String,
    val lastName: String,
    val login: String,
    val email: String,
    val sessionLastAccessedTime: Long,
    val sessionMaxInactiveInterval: Int,
    val jSessionId: String
)
