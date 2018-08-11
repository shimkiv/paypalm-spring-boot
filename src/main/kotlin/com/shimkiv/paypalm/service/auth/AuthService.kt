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

package com.shimkiv.paypalm.service.auth

import com.shimkiv.paypalm.service.auth.user.PaypalmUserDetailsService
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

/**
 * Authentication service
 *
 * @author Serhii Shymkiv
 */

@Service
class AuthService(
    private val passwordEncoder: BCryptPasswordEncoder,
    private val paypalmUserDetailsService: PaypalmUserDetailsService
) {
    fun buildAuthManager(
        authenticationManagerBuilder: AuthenticationManagerBuilder
    ) {
        authenticationManagerBuilder
            .userDetailsService(
                paypalmUserDetailsService
            ).passwordEncoder(
                passwordEncoder
            )
    }
}
