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

import com.shimkiv.paypalm.model.dto.role.Role
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.Size

/**
 * User DTO(s)
 *
 * @author Serhii Shymkiv
 */

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    @Column(name = "name")
    @field:Size(max = 30)
    val name: String,
    @Column(name = "password")
    @field:Size(max = 128)
    val password: String,
    @OneToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL]
    )
    @JoinColumn(name = "settings_id")
    var userSettings: UserSettings,
    @OneToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL]
    )
    @JoinColumn(name = "access_token_id")
    var userAccessToken: UserAccessToken?,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    val roles: Set<Role>
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
