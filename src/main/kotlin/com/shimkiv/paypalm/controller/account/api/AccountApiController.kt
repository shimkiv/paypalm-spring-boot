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

package com.shimkiv.paypalm.controller.account.api

import com.shimkiv.paypalm.model.vo.form.PaymentForm
import com.shimkiv.paypalm.properties.ACCOUNT_CLEAR_SHOPPING_CART_ENDPOINT
import com.shimkiv.paypalm.properties.ACCOUNT_ENDPOINT
import com.shimkiv.paypalm.properties.ACCOUNT_PAYMENT_ENDPOINT
import com.shimkiv.paypalm.properties.ACCOUNT_UPDATE_SHOPPING_CART_ENDPOINT
import com.shimkiv.paypalm.service.cart.ShoppingCartService
import com.shimkiv.paypalm.service.paypal.PaypalService
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import javax.validation.Valid

/**
 * Account API controller
 *
 * @author Serhii Shymkiv
 */

@RestController
@RequestMapping(ACCOUNT_ENDPOINT)
class AccountApiController(
    private val paypalService: PaypalService,
    private val shoppingCartService: ShoppingCartService
) {
    @PostMapping(ACCOUNT_CLEAR_SHOPPING_CART_ENDPOINT)
    fun clearShoppingCart() {
        shoppingCartService
            .clearShoppingCart()
    }

    @PostMapping(ACCOUNT_UPDATE_SHOPPING_CART_ENDPOINT)
    fun updateShoppingCart(
        @RequestParam productId: String,
        @RequestParam status: Boolean
    ) {
        shoppingCartService
            .updateShoppingCart(
                productId,
                status
            )
    }

    @PostMapping(ACCOUNT_PAYMENT_ENDPOINT)
    fun processPaymentRequest(
        principal: Principal,
        @Valid paymentForm: PaymentForm,
        bindingResult: BindingResult
    ) =
        paypalService
            .processPayment(
                userName = principal.name,
                paymentForm = paymentForm,
                bindingResult = bindingResult
            )
}
