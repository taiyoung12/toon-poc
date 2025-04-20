package com.comics.lezhin.toon.poc.common.utils

import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Component

@Component
class PasswordMatcher {
    fun matches(
        rawPassword: String?,
        encodedPassword: String?,
    ): Boolean = BCrypt.checkpw(rawPassword, encodedPassword)
}
