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

package com.shimkiv.paypalm.controller

import com.shimkiv.paypalm.properties.*
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * Base controller
 *
 * @author Serhii Shymkiv
 */

@Controller
class BaseController {
    @GetMapping(ROOT_ENDPOINT)
    fun renderIndexPage() =
        renderInitPage()

    @GetMapping(LOGIN_ENDPOINT)
    fun renderLoginPage() =
        renderInitPage()

    private fun renderInitPage() =
        if (SecurityContextHolder
                .getContext()
                .authentication !is
                    AnonymousAuthenticationToken) {
            "$REDIRECT_MAPPING$ACCOUNT_ENDPOINT"
        } else {
            LOGIN_VIEW_NAME
        }
}
