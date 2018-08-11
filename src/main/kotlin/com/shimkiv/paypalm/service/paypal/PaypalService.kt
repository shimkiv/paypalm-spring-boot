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

package com.shimkiv.paypalm.service.paypal

import com.fasterxml.jackson.databind.ObjectMapper
import com.shimkiv.paypalm.model.cart.ShoppingCart
import com.shimkiv.paypalm.model.dto.order.Order
import com.shimkiv.paypalm.model.dto.user.User
import com.shimkiv.paypalm.model.dto.user.UserAccessToken
import com.shimkiv.paypalm.model.enums.PaymentMethod
import com.shimkiv.paypalm.model.enums.PaymentResponseState
import com.shimkiv.paypalm.model.enums.PaymentType
import com.shimkiv.paypalm.model.vo.form.PaymentForm
import com.shimkiv.paypalm.model.vo.paypal.*
import com.shimkiv.paypalm.properties.PAYPAL_OAUTH_ENDPOINT
import com.shimkiv.paypalm.properties.PAYPAL_PAYMENT_ENDPOINT
import com.shimkiv.paypalm.service.dao.order.OrderService
import com.shimkiv.paypalm.service.dao.user.UserService
import mu.KotlinLogging
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.validation.BindingResult
import org.springframework.web.client.exchange
import java.text.NumberFormat
import java.util.*

/**
 * Paypal service
 *
 * @author Serhii Shymkiv
 */

private val kLogger = KotlinLogging.logger {}

@Service
class PaypalService(
    private val currency: Currency,
    private val paypalManager: Paypal,
    private val userService: UserService,
    private val orderService: OrderService,
    private val textEncryptor: TextEncryptor,
    private val shoppingCart: ShoppingCart
) {
    fun processPayment(
        userName: String,
        paymentForm: PaymentForm,
        bindingResult: BindingResult
    ): PaymentResponse =
        if (!bindingResult.hasErrors()) {
            try {
                val user =
                    userService
                        .findByName(
                            userName
                        )!!

                performPayment(
                    userAccessToken = getUserAccessToken(
                        user
                    ),
                    payload = generatePaymentPayload(
                        paymentForm
                    )
                ).also {
                    if (
                        PaymentResponseState.FAILED.value.equals(
                            other = it.state,
                            ignoreCase = true
                        )
                    ) {
                        kLogger.info("Payment failure: {}", it)
                    }

                    orderService
                        .saveAndFlush(
                            Order(
                                userId = user
                                    .id!!,
                                paypalId = it
                                    .id,
                                creationDate = Calendar
                                    .getInstance(),
                                cardType = paymentForm
                                    .cardType,
                                cardNumber = textEncryptor
                                    .encrypt(
                                        paymentForm
                                            .cardNumber
                                    ),
                                amount = numberFormat()
                                    .format(
                                        shoppingCart
                                            .totalAmount
                                    ).toDouble(),
                                status = it
                                    .state,
                                link = when {
                                    it.links != null && !it.links.isEmpty() ->
                                        it.links.first().href
                                    else -> null
                                },
                                productList = shoppingCart
                                    .products
                                    .toList()
                            )
                        )
                }
            } catch (e: Exception) {
                kLogger.info("Payment failure with exception:", e)

                PaymentResponse()
            } finally {
                shoppingCart
                    .clear()
            }
        } else {
            kLogger.debug(
                "Payment form validation errors: {}",
                bindingResult
            )

            PaymentResponse()
        }

    private fun performPayment(
        userAccessToken: UserAccessToken,
        payload: String
    ): PaymentResponse =
        try {
            paypalManager
                .client
                .build()
                .exchange<PaymentResponse>(
                    PAYPAL_PAYMENT_ENDPOINT,
                    HttpMethod.POST,
                    HttpEntity(
                        payload,
                        HttpHeaders().apply {
                            add(
                                AUTHORIZATION,
                                "${userAccessToken.tokenType} ${userAccessToken.accessToken}"
                            )
                            contentType =
                                    MediaType
                                        .APPLICATION_JSON
                        }
                    )
                ).let {
                    if (it.statusCode.is2xxSuccessful) {
                        return@let it.body!!
                    } else {
                        return@let PaymentResponse()
                    }
                }
        } catch (e: Exception) {
            PaymentResponse()
        }

    private fun getUserAccessToken(
        user: User
    ) =
        if (isValidAccessToken(user.userAccessToken)) {
            user.userAccessToken!!
        } else {
            getUserNewAccessToken(
                user.userSettings
                    .paypalClientId!!,
                user.userSettings
                    .paypalSecret!!
            ).let {
                userService
                    .updateAccessToken(
                        user.name,
                        it
                    )

                return@let it
            }
        }

    private fun getUserNewAccessToken(
        paypalClientId: String,
        paypalSecret: String
    ): UserAccessToken =
        if (
            paypalClientId.isNotBlank() &&
            paypalSecret.isNotBlank()
        ) {
            try {
                paypalManager
                    .client
                    .basicAuthorization(
                        paypalClientId,
                        paypalSecret
                    ).build()
                    .exchange<AccessTokenResponse>(
                        PAYPAL_OAUTH_ENDPOINT,
                        HttpMethod.POST,
                        HttpEntity(
                            LinkedMultiValueMap<String, Any>().apply {
                                this["grant_type"] = "client_credentials"
                            },
                            HttpHeaders().apply {
                                contentType =
                                        MediaType
                                            .APPLICATION_FORM_URLENCODED
                            }
                        )
                    ).let {
                        if (it.statusCode.is2xxSuccessful) {
                            return@let UserAccessToken(
                                expiresIn = it.body?.expiresIn?.toInt(),
                                tokenType = it.body?.tokenType,
                                accessToken = it.body?.accessToken
                            )
                        } else {
                            return@let UserAccessToken()
                        }
                    }
            } catch (e: Exception) {
                UserAccessToken()
            }
        } else {
            UserAccessToken()
        }

    private fun generatePaymentPayload(
        paymentForm: PaymentForm
    ) =
        ObjectMapper()
            .writeValueAsString(
                PaymentRequest(
                    intent = PaymentType.SALE.value,
                    payer = Payer(
                        paymentMethod = PaymentMethod.CREDIT_CARD.value,
                        fundingInstruments = listOf(
                            CreditCardWrapper(
                                creditCard = CreditCard(
                                    type = paymentForm
                                        .cardType,
                                    number = paymentForm
                                        .cardNumber,
                                    expireMonth = paymentForm
                                        .expMonth,
                                    expireYear = paymentForm
                                        .expYear,
                                    cvv2 = paymentForm
                                        .cardCvv,
                                    firstName = paymentForm
                                        .cardHolderName
                                        .split("\\s".toRegex())[0],
                                    lastName = paymentForm
                                        .cardHolderName
                                        .split("\\s".toRegex())
                                        .let {
                                            if (it.size > 1)
                                                return@let it[1]
                                            else
                                                return@let ""
                                        }
                                )
                            )
                        )
                    ),
                    transactions = listOf(
                        Transaction(
                            amount = Amount(
                                total = numberFormat()
                                    .format(
                                        shoppingCart
                                            .totalAmount
                                    ),
                                currency = currency.currencyCode
                            ),
                            description = ""
                        )
                    )
                )
            )

    private fun isValidAccessToken(
        userAccessToken: UserAccessToken?
    ) =
        try {
            userAccessToken
                ?.creationDate
                ?.let {
                    it.add(
                        Calendar.SECOND,
                        userAccessToken.expiresIn!!
                    )

                    it.set(Calendar.SECOND, 0)
                    it.set(Calendar.MILLISECOND, 0)

                    return@let Calendar
                        .getInstance()
                        .apply {
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }.before(it)
                }

            false
        } catch (e: Exception) {
            false
        }

    private fun numberFormat() =
        NumberFormat.getInstance()
            .apply {
                maximumFractionDigits =
                        currency
                            .defaultFractionDigits
                minimumFractionDigits =
                        currency
                            .defaultFractionDigits
                isGroupingUsed =
                        false
            }
}
