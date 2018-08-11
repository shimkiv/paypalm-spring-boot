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

package com.shimkiv.paypalm.service.ehcache

import mu.KotlinLogging
import org.ehcache.event.CacheEvent
import org.ehcache.event.CacheEventListener

/**
 * Ehcache event logger
 *
 * @author Serhii Shymkiv
 */

private val kLogger = KotlinLogging.logger {}

class EhcacheEventLogger : CacheEventListener<Any, Any> {
    override fun onEvent(event: CacheEvent<out Any, out Any>?) {
        kLogger.info {
            """|
               |
               |Event: ${event?.type},
               |Key  : ${event?.key}
               |
               |
            """.trimMargin()
        }
    }
}
