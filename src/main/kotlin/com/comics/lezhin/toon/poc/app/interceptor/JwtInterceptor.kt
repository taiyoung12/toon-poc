package com.comics.lezhin.toon.poc.app.interceptor

import com.comics.lezhin.toon.poc.app.token.JwtValidator
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class JwtInterceptor(
    private val jwtValidator: JwtValidator,
) : HandlerInterceptor {
    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER = "Bearer "
        private const val USER_ID = "userId"
        private const val UNAUTHORIZED = "Unauthorized"
    }

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        val header = request.getHeader(AUTHORIZATION_HEADER)

        if (header != null && header.startsWith(BEARER)) {
            val token = header.removePrefix(BEARER)
            if (jwtValidator.validate(token)) {
                val userId = jwtValidator.extractSubject(token)
                request.setAttribute(USER_ID, userId)
                return true
            }
        }

        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.write(UNAUTHORIZED)
        return false
    }
}
