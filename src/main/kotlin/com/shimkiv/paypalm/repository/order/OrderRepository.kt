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

package com.shimkiv.paypalm.repository.order

import com.shimkiv.paypalm.model.dto.order.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * Order repository
 *
 * @author Serhii Shymkiv
 */

@Repository
interface OrderRepository : JpaRepository<Order, Int> {
    @Query(value = "SELECT o FROM Order o LEFT JOIN o.productList p WHERE o.userId = :userId AND p.id = :productId")
    fun findByUserIdAndProductId(
        userId: Int,
        productId: Int
    ): List<Order>
}
