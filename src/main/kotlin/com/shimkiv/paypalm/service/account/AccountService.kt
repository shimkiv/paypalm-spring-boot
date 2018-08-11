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

package com.shimkiv.paypalm.service.account

import com.shimkiv.paypalm.component.CommonUtils
import com.shimkiv.paypalm.model.cart.ShoppingCart
import com.shimkiv.paypalm.properties.*
import com.shimkiv.paypalm.service.dao.order.OrderService
import com.shimkiv.paypalm.service.dao.product.ProductService
import com.shimkiv.paypalm.service.dao.user.UserService
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import org.springframework.ui.set

/**
 * Account service
 *
 * @author Serhii Shymkiv
 */

@Service
class AccountService(
    private val userService: UserService,
    private val productService: ProductService,
    private val orderService: OrderService,
    private val commonUtils: CommonUtils,
    private val shoppingCart: ShoppingCart
) {
    fun updateAccountIndexModel(
        userName: String,
        model: Model
    ) {
        addUserToModel(
            userName,
            model
        )

        addProductListToModel(
            model
        )

        addShoppingCartToModel(
            model
        )
    }

    fun updateAccountStatisticsModel(
        productId: String,
        userName: String,
        model: Model
    ) {
        addUserToModel(
            userName,
            model
        )

        addProductToModel(
            commonUtils
                .uniqueRefToPkey(
                    productId
                ),
            model
        )

        addOrderListAndTotalToModel(
            userService
                .findByName(userName)?.id!!,
            commonUtils
                .uniqueRefToPkey(
                    productId
                ),
            model
        )
    }

    fun updateAccountCheckoutModel(
        userName: String,
        model: Model
    ) {
        addUserToModel(
            userName,
            model
        )

        addShoppingCartToModel(
            model
        )
    }

    private fun addUserToModel(
        userName: String,
        model: Model
    ) {
        model[USER_ATTR] =
                userService
                    .findByName(
                        userName
                    )!!
    }

    private fun addProductToModel(
        productId: Int,
        model: Model
    ) {
        model[PRODUCT_ATTR] =
                productService
                    .findById(
                        productId
                    )
    }

    private fun addShoppingCartToModel(
        model: Model
    ) {
        model[SHOPPING_CART_ATTR] =
                shoppingCart
    }

    private fun addProductListToModel(
        model: Model
    ) {
        model[PRODUCT_LIST_ATTR] =
                productService
                    .findAll()
    }

    private fun addOrderListAndTotalToModel(
        userId: Int,
        productId: Int,
        model: Model
    ) {
        val orderList =
            orderService
                .findByUserIdAndProductId(
                    userId,
                    productId
                )

        model[ORDER_LIST_ATTR] =
                orderList
        model[TOTAL_AMOUNT_ATTR] =
                orderList
                    .sumByDouble {
                        it.amount
                    }
    }
}
