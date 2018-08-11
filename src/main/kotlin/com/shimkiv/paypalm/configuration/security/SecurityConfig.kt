/*
 * All materials herein: Copyright (c) 2000-2018 Serhii Shymkiv. All Rights Reserved.
 *
 * These materials are owned by Serhii Shymkiv and are protected by copyright laws
 * and international copyright treaties, as well as other intellectual property laws
 * and treaties.
 *
 * All right, title and interest in the copyright, confidential information,
 * patents, design rights and all other intellectual property rights of
 * whatsoever nature in and to these materials are and shall remain the sole
 * and exclusive property of Serhii Shymkiv.
 */

package com.shimkiv.paypalm.configuration.security

import com.shimkiv.paypalm.properties.*
import com.shimkiv.paypalm.service.auth.AuthService
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


/**
 * Security configuration
 *
 * @author Serhii Shymkiv
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val authService: AuthService
) {
    private val prodEndpoints =
        arrayOf(
            ROOT_ENDPOINT,
            LOGIN_ENDPOINT,
            LOGOUT_ENDPOINT,
            WILDCARD_ERROR_ENDPOINT
        )
    private val devEndpoints =
        arrayOf(
            *prodEndpoints,
            WILDCARD_H2_CONSOLE_ENDPOINT
        )

    @Bean
    @Profile("prod")
    fun prodWebSecurityConfigurerAdapter() =
        object : WebSecurityConfigurerAdapter() {
            override fun configure(auth: AuthenticationManagerBuilder) {
                authService
                    .buildAuthManager(
                        auth
                    )
            }

            override fun configure(httpSecurity: HttpSecurity) {
                configureWebSecurity(
                    httpSecurity,
                    prodEndpoints
                )
            }
        }

    @Bean
    @Profile("dev")
    fun devWebSecurityConfigurerAdapter() =
        object : WebSecurityConfigurerAdapter() {
            override fun configure(auth: AuthenticationManagerBuilder) {
                authService
                    .buildAuthManager(
                        auth
                    )
            }

            override fun configure(httpSecurity: HttpSecurity) {
                configureWebSecurity(
                    httpSecurity,
                    devEndpoints
                )

                httpSecurity
                    .csrf()
                    .disable()
                httpSecurity
                    .headers()
                    .frameOptions()
                    .disable()
            }
        }

    private fun configureWebSecurity(
        httpSecurity: HttpSecurity,
        endpoints: Array<String>
    ) {
        httpSecurity
            .authorizeRequests()
            .requestMatchers(EndpointRequest.to(HEALTH_ENDPOINT, PROMETHEUS_ENDPOINT)).permitAll()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .antMatchers(*endpoints).permitAll()
            .requestMatchers(EndpointRequest.toAnyEndpoint()).hasAuthority(ROLE_ADMIN)
            .antMatchers(WILDCARD_ACCOUNT_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage(ROOT_ENDPOINT)
            .failureUrl(LOGIN_ERROR_ENDPOINT)
            .defaultSuccessUrl(ACCOUNT_ENDPOINT)
            .usernameParameter(USER_NAME_PARAM)
            .passwordParameter(USER_PWD_PARAM)
            .and()
            .rememberMe()
            .and()
            .logout()
            .logoutSuccessUrl(ROOT_ENDPOINT)
            .permitAll()
    }
}
