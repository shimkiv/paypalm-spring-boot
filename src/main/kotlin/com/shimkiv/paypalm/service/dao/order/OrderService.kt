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

package com.shimkiv.paypalm.service.dao.order

import com.shimkiv.paypalm.model.dto.order.Order
import com.shimkiv.paypalm.repository.order.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Order service
 *
 * @author Serhii Shymkiv
 */

@Service
@Transactional
class OrderService(
    private val orderRepository: OrderRepository
) {
    fun findByUserIdAndProductId(
        userId: Int,
        productId: Int
    ) =
        orderRepository
            .findByUserIdAndProductId(
                userId,
                productId
            )

    fun saveAndFlush(order: Order) =
        orderRepository
            .saveAndFlush(
                order
            )
}
