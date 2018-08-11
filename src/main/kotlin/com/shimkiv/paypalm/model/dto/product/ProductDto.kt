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

package com.shimkiv.paypalm.model.dto.product

import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.Digits
import javax.validation.constraints.Size

/**
 * Product DTO(s)
 *
 * @author Serhii Shymkiv
 */

@Entity
@Table(name = "product")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    @Column(name = "name")
    @field:Size(max = 50)
    val name: String,
    @Column(name = "description")
    @field:Size(max = 256)
    val description: String,
    @Column(name = "price")
    @field:Digits(integer = 5, fraction = 2)
    val price: Double
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
