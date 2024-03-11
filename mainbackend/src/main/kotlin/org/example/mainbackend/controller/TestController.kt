package org.example.mainbackend.controller

import org.example.mainbackend.service.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val service: Service,
) {
    @GetMapping("/")
    fun home(): String {
        val a = service.doRecommendation()
        return "home $a"
    }
}
