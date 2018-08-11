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
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size

/**
 * User access token DTO(s)
 *
 * @author Serhii Shymkiv
 */

@Entity
@Table(name = "access_token")
data class UserAccessToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    var creationDate: Calendar? =
        Calendar
            .getInstance(),
    @Column(name = "expires_in")
    var expiresIn: Int? = 0,
    @Column(name = "token_type")
    @field:Size(max = 128)
    var tokenType: String? = null,
    @Column(name = "access_token")
    @field:Size(max = 256)
    var accessToken: String? = null
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
