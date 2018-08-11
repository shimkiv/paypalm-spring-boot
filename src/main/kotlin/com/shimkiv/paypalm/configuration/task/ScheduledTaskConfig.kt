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

package com.shimkiv.paypalm.configuration.task

import com.shimkiv.paypalm.properties.PaypalmProperties
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar

/**
 * Scheduled tasks configuration
 *
 * @author Serhii Shymkiv
 */

@Configuration
class ScheduledTaskConfig(
    private val paypalmProperties: PaypalmProperties
) : SchedulingConfigurer {
    override fun configureTasks(
        taskRegistrar: ScheduledTaskRegistrar
    ) {
        taskRegistrar
            .setTaskScheduler(
                ThreadPoolTaskScheduler()
                    .apply {
                        poolSize =
                                paypalmProperties
                                    .schedulerThreadPool
                                    .size
                        setThreadNamePrefix(
                            paypalmProperties
                                .schedulerThreadPool
                                .namePrefix
                        )
                    }.apply {
                        initialize()
                    }
            )
    }
}
