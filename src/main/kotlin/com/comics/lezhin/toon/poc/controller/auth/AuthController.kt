package com.comics.lezhin.toon.poc.controller.auth

import com.comics.lezhin.toon.poc.app.response.Response
import com.comics.lezhin.toon.poc.application.auth.AuthApplication
import com.comics.lezhin.toon.poc.common.code.AuthCode
import com.comics.lezhin.toon.poc.controller.request.SigninRequest
import com.comics.lezhin.toon.poc.controller.response.SigninResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authApplication: AuthApplication,
) {
    @PostMapping("/signin")
    fun signin(
        @RequestBody request: SigninRequest,
    ): Response<SigninResponse> {
        val token =
            authApplication.signin(
                email = request.email,
                password = request.password,
            )
        return Response.success(AuthCode.SUCCESS, SigninResponse(token))
    }
}
