/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import xyz.dvnlabs.approvalapi.service.UserService
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenFilter : OncePerRequestFilter() {
    @Autowired
    private lateinit var jwtUtils: JwtUtils

    @Autowired
    private lateinit var userService: UserService

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            val jwt = parseJwt(request)
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                val usernameJwt = jwtUtils.getUsernameFromToken(jwt)
                val userDetails = userService.loadUserByUsername(usernameJwt)
                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        chain.doFilter(request, response)
    }

    private fun parseJwt(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")
        return if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            authHeader.substring(7, authHeader.length)
        } else null

    }

}