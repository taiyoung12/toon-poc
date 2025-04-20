package com.comics.lezhin.toon.poc.common.utils

import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Component

@Component
class PasswordMatcher {
    @Deprecated("회원가입 있는 이후에 밀어넣기")
    fun matches(
        rawPassword: String?,
        encodedPassword: String?,
    ): Boolean = BCrypt.checkpw(rawPassword, encodedPassword)
}
