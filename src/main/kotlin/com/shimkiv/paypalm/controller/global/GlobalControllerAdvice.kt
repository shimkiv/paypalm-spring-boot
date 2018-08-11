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

package com.shimkiv.paypalm.controller.global

import com.shimkiv.paypalm.properties.CURRENCY_ATTR
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute
import java.util.*

/**
 * Global controller advice
 *
 * @author Serhii Shymkiv
 */

@ControllerAdvice
class GlobalControllerAdvice(
    private val currency: Currency
) {
    @ModelAttribute
    fun addDefaultsToModel(model: Model) {
        model[CURRENCY_ATTR] =
                currency
    }
}
