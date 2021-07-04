/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.security

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import xyz.dvnlabs.approvalapi.core.config.Configuration
import java.util.*

@Component
class JwtUtils {

    @Autowired
    lateinit var configuration: Configuration

    companion object {
        const val ONE_DAY_IN_MS: Long = 60 * 60 * 24
    }

    fun generateToken(authentication: Authentication): String {
        val principal = authentication.principal as UserDetails
        return Jwts.builder()
            .setSubject(principal.username)
            .setIssuedAt(Date())
            .setIssuer("Skripshit")
            .setExpiration(Date(System.currentTimeMillis() + configuration.expiredDay * ONE_DAY_IN_MS))
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(configuration.tokenSecret)))
            .compact()
    }

    fun getUsernameFromToken(token: String): String {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(configuration.tokenSecret)))
            .build()
            .parseClaimsJws(token).body.subject
    }

    fun validateJwtToken(authToken: String?): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(configuration.tokenSecret)))
                .build()
                .parseClaimsJws(authToken)
            return true
        } catch (e: SecurityException) {
            println("Invalid JWT signature: ${e.message}")
        } catch (e: MalformedJwtException) {
            println("Invalid JWT token: ${e.message}")
        } catch (e: ExpiredJwtException) {
            println("JWT token is expired: ${e.message}")
        } catch (e: UnsupportedJwtException) {
            println("JWT token is unsupported: ${e.message}")
        } catch (e: IllegalArgumentException) {
            println("JWT claims string is empty: ${e.message}")
        }
        return false
    }
}
