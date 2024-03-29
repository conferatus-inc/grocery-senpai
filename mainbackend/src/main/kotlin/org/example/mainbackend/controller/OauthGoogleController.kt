package org.example.mainbackend.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/oauth")
class OauthGoogleController {
    @GetMapping("/user")
    fun user(@AuthenticationPrincipal principal: OAuth2User) {
        println(principal.authorities)
    }
}