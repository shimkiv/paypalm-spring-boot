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

package com.shimkiv.paypalm.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

/**
 * Application properties
 *
 * @author Serhii Shymkiv
 */

const val APPLICATION_LABEL: String = "application"
const val INSTANCE_LABEL: String = "instance"

const val LOGIN_VIEW_NAME: String = "login"
const val ACCOUNT_INDEX_VIEW_NAME: String = "account/index"
const val ACCOUNT_STATISTICS_VIEW_NAME: String = "account/statistics"
const val ACCOUNT_CHECKOUT_VIEW_NAME: String = "account/checkout"

const val DEFAULT_LANG: String = "en"
const val DEFAULT_RESOURCE_BUNDLE: String = "labels/labels"
const val DEFAULT_LANG_COOKIE_NAME: String = "paypalmLocale"

const val DEFAULT_CACHE_NAME: String = "default-cache"
const val USER_CACHE_NAME: String = "user-cache"

const val HEALTH_ENDPOINT: String = "health"
const val PROMETHEUS_ENDPOINT: String = "prometheus"

const val ROOT_ENDPOINT: String = "/"
const val LOGIN_ENDPOINT: String = "/login"
const val LOGIN_ERROR_ENDPOINT: String = "$LOGIN_ENDPOINT?error=true"
const val LOGOUT_ENDPOINT: String = "/logout"
const val ACCOUNT_ENDPOINT: String = "/account"
const val ACCOUNT_UPDATE_ENDPOINT: String = "/update"
const val ACCOUNT_CLEAR_SHOPPING_CART_ENDPOINT: String = "/clear-shopping-cart"
const val ACCOUNT_UPDATE_SHOPPING_CART_ENDPOINT: String = "/update-shopping-cart"
const val ACCOUNT_PAYMENT_ENDPOINT: String = "/payment"
const val WILDCARD_ACCOUNT_STATISTICS_ENDPOINT: String = "/statistics/**"
const val WILDCARD_ACCOUNT_CHECKOUT_ENDPOINT: String = "/checkout/**"
const val WILDCARD_ERROR_ENDPOINT: String = "/error/**"
const val WILDCARD_H2_CONSOLE_ENDPOINT: String = "/h2/**"
const val WILDCARD_ACCOUNT_ENDPOINT: String = "$ACCOUNT_ENDPOINT/**"

const val PAYPAL_OAUTH_ENDPOINT: String = "/oauth2/token"
const val PAYPAL_PAYMENT_ENDPOINT: String = "/payments/payment"

const val LANG_PARAM: String = "lang"
const val USER_NAME_PARAM: String = "j_username"
const val USER_PWD_PARAM: String = "j_password"
const val USER_ATTR: String = "user"
const val CURRENCY_ATTR: String = "currency"
const val SHOPPING_CART_ATTR: String = "shoppingCart"
const val PRODUCT_ATTR: String = "product"
const val PRODUCT_LIST_ATTR: String = "productList"
const val ORDER_LIST_ATTR: String = "orderList"
const val TOTAL_AMOUNT_ATTR: String = "totalAmount"

const val REDIRECT_MAPPING: String = "redirect:"

const val ROLE_ADMIN: String = "ROLE_ADMIN"
const val ROLE_USER: String = "ROLE_USER"

const val DEFAULT_CURRENCY_CODE: String = "USD"

const val USER_NOT_FOUND_MSG: String = "User '{}' not found !"

@Configuration
@ConfigurationProperties("server")
class ServerProperties {
    var ajp =
        Ajp()

    inner class Ajp {
        var port: Int = 8009

        lateinit var protocol: String
    }
}

@Configuration
@ConfigurationProperties("spring")
class SpringProperties {
    var application =
        Application()

    inner class Application {
        lateinit var name: String
    }
}

@Configuration
@ConfigurationProperties("paypalm")
class PaypalmProperties {
    var security =
        Security()
    var cache =
        Cache()
    var schedulerThreadPool =
        SchedulerThreadPool()
    var paypal =
        Paypal()

    inner class Security {
        var bCryptStrength: Int = 10

        lateinit var cipherKey: String
        lateinit var cipherSalt: String
    }

    inner class Cache {
        var evictionPolicyDurationAmount: Long = 60L

        lateinit var evictionPolicyDurationTimeUnit: TimeUnit
    }

    inner class SchedulerThreadPool {
        var size: Int = 10

        lateinit var namePrefix: String
    }

    inner class Paypal {
        lateinit var apiUrl: String
    }
}
