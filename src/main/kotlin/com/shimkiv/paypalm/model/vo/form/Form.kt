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

package com.shimkiv.paypalm.model.vo.form

import java.io.Serializable
import javax.validation.constraints.Email
import javax.validation.constraints.Size

/**
 * Form VO(s)
 *
 * @author Serhii Shymkiv
 */

data class AccountSettingsForm(
    @field:Size(max = 128)
    val firstName: String?,
    @field:Size(max = 128)
    val lastName: String?,
    @field:Email
    @field:Size(max = 128)
    val email: String?,
    @field:Size(max = 128)
    val paypalClientId: String?,
    @field:Size(max = 128)
    val paypalSecret: String?
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}

data class PaymentForm(
    @field:Size(min = 1, max = 50)
    val cardType: String,
    @field:Size(min = 1, max = 100)
    val cardNumber: String,
    @field:Size(min = 2, max = 2)
    val expMonth: String,
    @field:Size(min = 4, max = 4)
    val expYear: String,
    @field:Size(min = 3, max = 4)
    val cardCvv: String,
    @field:Size(min = 1, max = 100)
    val cardHolderName: String
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
