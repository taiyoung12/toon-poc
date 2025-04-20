package com.comics.lezhin.toon.poc.application.auth

import com.comics.lezhin.toon.poc.app.annotation.ApplicationLayer
import com.comics.lezhin.toon.poc.app.exception.BaseException
import com.comics.lezhin.toon.poc.app.token.JwtGenerator
import com.comics.lezhin.toon.poc.common.code.AuthCode
import com.comics.lezhin.toon.poc.common.utils.PasswordMatcher
import com.comics.lezhin.toon.poc.service.auth.UserReader

@ApplicationLayer
class AuthApplication(
    private val userReader: UserReader,
    private val passwordMatcher: PasswordMatcher,
    private val jwtGenerator: JwtGenerator,
) {
    fun signin(
        email: String,
        password: String,
    ): String {
        val user = userReader.getUserBy(email = email)

        if (!passwordMatch(password, user.password)) {
            throw BaseException(AuthCode.NOT_MATCHED_USER_PASSWORD)
        }

        return jwtGenerator.generateAccessToken(user.getId().toString())
    }

    private fun passwordMatch(
        inputPassword: String,
        password: String,
    ) = inputPassword == password || passwordMatcher.matches(inputPassword, password)
}
