package com.comics.lezhin.toon.poc.service

import com.comics.lezhin.toon.poc.app.exception.BaseException
import com.comics.lezhin.toon.poc.common.code.AuthCode
import com.comics.lezhin.toon.poc.entity.UserEntity
import com.comics.lezhin.toon.poc.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserReader(
    private val userRepository: UserRepository,
) {
    @Transactional(readOnly = true)
    fun getUserBy(email: String): UserEntity {
        val userEntity =
            userRepository.findByEmail(email = email)
                ?: throw BaseException(AuthCode.NOT_FOUND_USER_BY_EMAIL)

        return userEntity
    }
}
