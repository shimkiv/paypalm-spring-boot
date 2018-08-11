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

package com.shimkiv.paypalm.model.dto.user

import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.Size

/**
 * User settings DTO(s)
 *
 * @author Serhii Shymkiv
 */

@Entity
@Table(name = "user_settings")
data class UserSettings(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column(name = "first_name")
    @field:Size(max = 128)
    var firstName: String? = null,
    @Column(name = "last_name")
    @field:Size(max = 128)
    var lastName: String? = null,
    @Column(name = "email")
    @field:Size(max = 128)
    @field:Email
    var email: String? = null,
    @Column(name = "paypal_client_id")
    @field:Size(max = 128)
    var paypalClientId: String? = null,
    @Column(name = "paypal_secret")
    @field:Size(max = 128)
    var paypalSecret: String? = null
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
