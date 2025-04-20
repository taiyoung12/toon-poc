package com.comics.lezhin.toon.poc.controller.health

import com.comics.lezhin.toon.poc.app.response.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${api.prefix}")
class PingController {
    @GetMapping("/ping")
    fun pong(): Response<String> = Response.success("pong")
}
