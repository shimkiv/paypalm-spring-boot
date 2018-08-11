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

package com.shimkiv.paypalm.model.cart

import com.shimkiv.paypalm.model.dto.product.Product
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import org.springframework.web.context.WebApplicationContext
import java.io.Serializable

/**
 * Shopping cart
 *
 * @author Serhii Shymkiv
 */

@Component
@Scope(
    value = WebApplicationContext.SCOPE_SESSION,
    proxyMode = ScopedProxyMode.TARGET_CLASS
)
class ShoppingCart : Serializable {
    var totalAmount: Double = 0.00
    val products: MutableSet<Product> = mutableSetOf()

    companion object {
        private const val serialVersionUID: Long = 1L
    }

    fun contains(product: Product) =
        products
            .contains(
                product
            )

    fun addProduct(
        product: Product
    ) {
        products.add(product)
        totalAmount += product.price
    }

    fun removeProduct(
        product: Product
    ) {
        products.remove(product)

        if (totalAmount > 0) {
            totalAmount -= product.price
        } else {
            totalAmount = 0.00
        }
    }

    fun clear() {
        products.clear()
        totalAmount = 0.00
    }

    fun size() =
        products
            .size
}
