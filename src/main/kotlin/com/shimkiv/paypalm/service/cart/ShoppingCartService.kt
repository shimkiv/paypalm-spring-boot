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

package com.shimkiv.paypalm.service.cart

import com.shimkiv.paypalm.component.CommonUtils
import com.shimkiv.paypalm.model.cart.ShoppingCart
import com.shimkiv.paypalm.service.dao.product.ProductService
import org.springframework.stereotype.Service

/**
 * Shopping cart service
 *
 * @author Serhii Shymkiv
 */

@Service
class ShoppingCartService(
    private val productService: ProductService,
    private val commonUtils: CommonUtils,
    private val shoppingCart: ShoppingCart
) {
    fun clearShoppingCart() {
        shoppingCart
            .clear()
    }

    fun updateShoppingCart(
        productId: String,
        status: Boolean
    ) {
        val product =
            productService
                .findById(
                    commonUtils
                        .uniqueRefToPkey(
                            productId
                        )
                )

        if (status) {
            shoppingCart
                .addProduct(
                    product
                )
        } else {
            shoppingCart
                .removeProduct(
                    product
                )
        }
    }
}
