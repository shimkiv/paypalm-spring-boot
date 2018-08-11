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

package com.shimkiv.paypalm.controller.account

import com.shimkiv.paypalm.model.vo.form.AccountSettingsForm
import com.shimkiv.paypalm.properties.*
import com.shimkiv.paypalm.service.account.AccountService
import com.shimkiv.paypalm.service.dao.user.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal
import javax.validation.Valid

/**
 * Account controller
 *
 * @author Serhii Shymkiv
 */

@Controller
@RequestMapping(ACCOUNT_ENDPOINT)
class AccountController(
    private val userService: UserService,
    private val accountService: AccountService
) {
    @GetMapping
    fun renderAccountIndexPage(
        principal: Principal,
        model: Model
    ) =
        accountService
            .updateAccountIndexModel(
                userName = principal.name,
                model = model
            ).let {
                ACCOUNT_INDEX_VIEW_NAME
            }

    @GetMapping(WILDCARD_ACCOUNT_STATISTICS_ENDPOINT)
    fun renderAccountStatisticsPage(
        @RequestParam productId: String,
        principal: Principal,
        model: Model
    ) =
        accountService
            .updateAccountStatisticsModel(
                productId = productId,
                userName = principal.name,
                model = model
            ).let {
                ACCOUNT_STATISTICS_VIEW_NAME
            }

    @PostMapping(WILDCARD_ACCOUNT_CHECKOUT_ENDPOINT)
    fun renderAccountCheckoutPage(
        principal: Principal,
        model: Model
    ) =
        accountService
            .updateAccountCheckoutModel(
                userName = principal.name,
                model = model
            ).let {
                ACCOUNT_CHECKOUT_VIEW_NAME
            }

    @PostMapping(ACCOUNT_UPDATE_ENDPOINT)
    fun processAccountUpdate(
        principal: Principal,
        @Valid accountSettingsForm: AccountSettingsForm,
        bindingResult: BindingResult
    ) =
        userService
            .updateAccountSettings(
                userName = principal.name,
                accountSettingsForm = accountSettingsForm,
                bindingResult = bindingResult
            ).let {
                "$REDIRECT_MAPPING$ACCOUNT_ENDPOINT"
            }
}
