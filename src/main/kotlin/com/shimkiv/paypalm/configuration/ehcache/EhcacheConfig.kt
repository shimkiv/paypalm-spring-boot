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

package com.shimkiv.paypalm.configuration.ehcache

import com.shimkiv.paypalm.properties.DEFAULT_CACHE_NAME
import com.shimkiv.paypalm.properties.PaypalmProperties
import com.shimkiv.paypalm.properties.USER_CACHE_NAME
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit
import javax.cache.CacheManager
import javax.cache.configuration.MutableConfiguration
import javax.cache.expiry.Duration
import javax.cache.expiry.TouchedExpiryPolicy


/**
 * Ehcache configuration
 *
 * @author Serhii Shymkiv
 */

private val kLogger = KotlinLogging.logger {}

@Configuration
class EhcacheConfig(
    private val paypalmProperties: PaypalmProperties
) : JCacheManagerCustomizer {
    override fun customize(cacheManager: CacheManager?) {
        createCache(
            cacheManager,
            DEFAULT_CACHE_NAME,
            paypalmProperties
                .cache
                .evictionPolicyDurationTimeUnit,
            paypalmProperties
                .cache
                .evictionPolicyDurationAmount
        )

        createCache(
            cacheManager,
            USER_CACHE_NAME,
            paypalmProperties
                .cache
                .evictionPolicyDurationTimeUnit,
            paypalmProperties
                .cache
                .evictionPolicyDurationAmount
        )
    }

    private fun createCache(
        cacheManager: CacheManager?,
        cacheName: String,
        cacheDurationTimeUnit: TimeUnit,
        cacheDurationAmount: Long
    ) {
        cacheManager?.createCache(
            cacheName,
            MutableConfiguration<Any, Any>()
                .setExpiryPolicyFactory(
                    TouchedExpiryPolicy
                        .factoryOf(
                            Duration(
                                cacheDurationTimeUnit,
                                cacheDurationAmount
                            )
                        )
                )
                .setStoreByValue(false)
                .setStatisticsEnabled(true)
        ).also {
            kLogger.info("The \"$cacheName\" cache has been created ...")
        }
    }
}
