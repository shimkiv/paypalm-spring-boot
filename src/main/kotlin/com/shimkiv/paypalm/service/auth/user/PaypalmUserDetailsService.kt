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

package com.shimkiv.paypalm.service.auth.user

import com.shimkiv.paypalm.properties.USER_NOT_FOUND_MSG
import com.shimkiv.paypalm.service.dao.user.UserService
import mu.KotlinLogging
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * Paypalm user details service
 *
 * @author Serhii Shymkiv
 */

private val kLogger = KotlinLogging.logger {}

@Service
class PaypalmUserDetailsService(
    private val userService: UserService
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user =
            userService
                .findByName(
                    username
                )

        if (user != null) {
            return User
                .withUsername(
                    user.name
                ).password(
                    user.password
                ).authorities(
                    *(user.roles
                        .map {
                            it.name
                        }.toTypedArray())
                ).build()
        } else {
            kLogger.info(
                USER_NOT_FOUND_MSG,
                username
            )

            throw UsernameNotFoundException(
                USER_NOT_FOUND_MSG
            )
        }
    }
}
