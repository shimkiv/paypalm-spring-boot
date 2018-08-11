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

package com.shimkiv.paypalm.configuration.server

import com.shimkiv.paypalm.properties.ServerProperties
import org.apache.catalina.connector.Connector
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Server configuration
 *
 * @author Serhii Shymkiv
 */

@Configuration
class ServerConfig {
    @Bean
    fun tomcatServletWebServerFactory(
        ajpConnector: Connector
    ): TomcatServletWebServerFactory =
        TomcatServletWebServerFactory().apply {
            addAdditionalTomcatConnectors(
                ajpConnector
            )
        }

    @Bean
    fun registerAjpConnector(
        serverProperties: ServerProperties
    ): Connector =
        Connector(serverProperties.ajp.protocol).apply {
            port = serverProperties
                .ajp
                .port
        }
}
