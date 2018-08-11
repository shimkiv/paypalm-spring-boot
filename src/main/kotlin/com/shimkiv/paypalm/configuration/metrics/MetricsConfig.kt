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

package com.shimkiv.paypalm.configuration.metrics

import com.shimkiv.paypalm.properties.APPLICATION_LABEL
import com.shimkiv.paypalm.properties.INSTANCE_LABEL
import com.shimkiv.paypalm.properties.SpringProperties
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.InetAddress


/**
 * Metrics configuration
 *
 * @author Serhii Shymkiv
 */

@Configuration
class MetricsConfig {
    @Bean
    fun metricsCommonTags(
        springProperties: SpringProperties
    ) =
        MeterRegistryCustomizer<MeterRegistry> { registry ->
            registry.config()
                .commonTags(
                    APPLICATION_LABEL, springProperties.application.name,
                    INSTANCE_LABEL, InetAddress.getLocalHost().hostName
                )
        }
}
