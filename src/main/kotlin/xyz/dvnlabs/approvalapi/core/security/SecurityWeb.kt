/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import xyz.dvnlabs.approvalapi.service.UserService


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityWeb : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var authEntrypoint: AuthEntrypoint

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun tokenFilter(): TokenFilter {
        return TokenFilter()
    }

    override fun configure(web: WebSecurity?) {
        web?.ignoring()
            ?.antMatchers("/static/**")
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(http: HttpSecurity?) {
        http?.cors()?.and()
            ?.csrf()
            ?.disable()
            ?.exceptionHandling()?.authenticationEntryPoint(authEntrypoint)?.and()
            ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)?.and()
            ?.authorizeRequests()
            ?.antMatchers("/swagger-resources/**")?.permitAll()
            ?.antMatchers("/v2/api-docs/**")?.permitAll()
            ?.antMatchers("/webjars/**")?.permitAll()
            ?.antMatchers("/swagger-ui/**")?.permitAll()
            ?.antMatchers("/auth/**")?.permitAll()
            ?.antMatchers("/topic/**")?.permitAll()
            ?.antMatchers("/app/**")?.permitAll()
            ?.antMatchers("/live/**")?.permitAll()
            ?.anyRequest()
            ?.authenticated()

        http?.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userService)?.passwordEncoder(passwordEncoder())
    }


}