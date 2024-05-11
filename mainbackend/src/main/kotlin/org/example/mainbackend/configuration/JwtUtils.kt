package org.example.mainbackend.configuration

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.example.mainbackend.model.Role
import org.example.mainbackend.model.User
import org.example.mainbackend.service.AccountService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtUtils(
    private val accountService: AccountService,
) {
    private val accessTokenLifetime = (5000 * 60 * 1000).toLong()
    private val refreshTokenLifetime = (5000 * 60 * 1000).toLong()

    @Value("\${security.secret}")
    private val secret: String? = null

    fun createTokens(user: User): Tokens {
        val username = user.username
        val algorithm = Algorithm.HMAC256(secret!!.toByteArray())
        val accessToken =
            JWT.create()
                .withSubject(username)
                .withExpiresAt(Date(System.currentTimeMillis() + accessTokenLifetime))
                .withIssuer("access")
                .withIssuedAt(Date())
                .withClaim(
                    "roles",
                    user.roles.map { role: Role -> role.name.toString() },
                )
                .sign(algorithm)
        val refreshToken =
            JWT.create()
                .withSubject(username)
                .withExpiresAt(Date(System.currentTimeMillis() + refreshTokenLifetime))
                .withIssuer("refresh")
                .withIssuedAt(Date())
                .withClaim(
                    "roles",
                    user.roles.map { role: Role -> role.name.toString() },
                )
                .sign(algorithm)
        accountService.updateAccessToken(username, accessToken)
        accountService.updateRefreshToken(username, refreshToken)
        return Tokens(accessToken, refreshToken)
    }

    fun createAccessToken(user: User): String {
        val algorithm = Algorithm.HMAC256(secret!!.toByteArray())
        val username = user.username
        return JWT.create()
            .withSubject(username)
            .withExpiresAt(Date(System.currentTimeMillis() + accessTokenLifetime))
            .withIssuer("/api/accounts/login")
            .withIssuedAt(Date())
            .withClaim(
                "roles",
                user.roles.map { role: Role -> role.name.toString() },
            )
            .sign(algorithm)
    }

    fun decodeJWT(token: String?): DecodedJWT {
        val algorithm = Algorithm.HMAC256(secret)
        val verifier = JWT.require(algorithm).build()
        return verifier.verify(token)
    }

    @JvmRecord
    data class Tokens(val accessToken: String, val refreshToken: String)
}
