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

package com.shimkiv.paypalm.configuration

import com.shimkiv.paypalm.properties.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.CookieLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import java.util.*

/**
 * Application configuration
 *
 * @author Serhii Shymkiv
 */

@Configuration
class ApplicationConfig : WebMvcConfigurer {
    @Bean
    fun defaultCurrency(): Currency =
        Currency
            .getInstance(
                DEFAULT_CURRENCY_CODE
            )

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor =
        LocaleChangeInterceptor()
            .apply {
                paramName =
                        LANG_PARAM
                isIgnoreInvalidLocale =
                        true
            }

    @Bean
    fun localeResolver() =
        CookieLocaleResolver()
            .apply {
                setDefaultLocale(
                    Locale(
                        DEFAULT_LANG
                    )
                )
                cookieName =
                        DEFAULT_LANG_COOKIE_NAME
            }

    @Bean
    fun messageSource() =
        ResourceBundleMessageSource()
            .apply {
                setBasename(
                    DEFAULT_RESOURCE_BUNDLE
                )
            }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry
            .addInterceptor(
                localeChangeInterceptor()
            )
    }
}
