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

package com.shimkiv.paypalm.service.dao.product

import com.shimkiv.paypalm.model.dto.product.Product
import com.shimkiv.paypalm.properties.DEFAULT_CACHE_NAME
import com.shimkiv.paypalm.repository.product.ProductRepository
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Product service
 *
 * @author Serhii Shymkiv
 */

@Service
@Transactional
@CacheConfig(cacheNames = [DEFAULT_CACHE_NAME])
class ProductService(
    private val productRepository: ProductRepository
) {
    @Cacheable
    fun findById(id: Int) =
        productRepository
            .findById(id)
            .get()

    @Cacheable
    fun findAll(): List<Product> =
        productRepository
            .findAll()
}
