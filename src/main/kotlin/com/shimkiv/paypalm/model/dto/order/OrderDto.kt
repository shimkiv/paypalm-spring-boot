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

package com.shimkiv.paypalm.model.dto.order

import com.shimkiv.paypalm.model.dto.product.Product
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Digits
import javax.validation.constraints.Size

/**
 * Order DTO(s)
 *
 * @author Serhii Shymkiv
 */

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @Column(name = "user_id")
    val userId: Int,
    @Column(name = "paypal_id")
    @field:Size(max = 128)
    val paypalId: String?,
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    val creationDate: Calendar,
    @Column(name = "card_number")
    @field:Size(max = 100)
    val cardNumber: String,
    @Column(name = "card_type")
    @field:Size(max = 50)
    val cardType: String,
    @Column(name = "amount")
    @field:Digits(integer = 5, fraction = 2)
    val amount: Double,
    @Column(name = "status")
    @field:Size(max = 20)
    val status: String,
    @Column(name = "link")
    @field:Size(max = 256)
    val link: String?,
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "order_products",
        joinColumns = [JoinColumn(name = "order_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "product_id", referencedColumnName = "id")]
    )
    var productList: List<Product>
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
