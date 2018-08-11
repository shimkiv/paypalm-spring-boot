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

package com.shimkiv.paypalm.configuration.paypal

import com.shimkiv.paypalm.model.vo.paypal.Paypal
import com.shimkiv.paypalm.properties.PaypalmProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * PayPal configuration
 *
 * @author Serhii Shymkiv
 */

@Configuration
class PaypalConfig(
    private val paypalmProperties: PaypalmProperties,
    private val restTemplateBuilder: RestTemplateBuilder
) {
    @Bean
    fun paypalManager() =
        Paypal(
            client = restTemplateBuilder
                .rootUri(
                    paypalmProperties
                        .paypal
                        .apiUrl
                )
        )
}
