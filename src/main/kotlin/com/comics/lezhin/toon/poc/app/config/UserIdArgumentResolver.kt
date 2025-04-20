package com.comics.lezhin.toon.poc.app.config
import com.comics.lezhin.toon.poc.app.annotation.UserId
import com.comics.lezhin.toon.poc.app.exception.BaseException
import com.comics.lezhin.toon.poc.common.code.JwtCode
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class UserIdArgumentResolver : HandlerMethodArgumentResolver {
    companion object {
        private const val USER_ID = "userId"
    }

    override fun supportsParameter(parameter: MethodParameter): Boolean =
        parameter.getParameterAnnotation(UserId::class.java) != null &&
            parameter.parameterType == Long::class.java

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any {
        val userIdAttribute =
            webRequest.getAttribute(USER_ID, NativeWebRequest.SCOPE_REQUEST)
                ?: throw BaseException(JwtCode.BLANK_TOKEN)

        val userId =
            userIdAttribute.toString().toLongOrNull()
                ?: throw BaseException(JwtCode.BLANK_TOKEN)

        return userId
    }
}
