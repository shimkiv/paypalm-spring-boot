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

package com.shimkiv.paypalm.model.vo.paypal

import com.fasterxml.jackson.annotation.JsonProperty
import com.shimkiv.paypalm.model.enums.PaymentResponseState
import org.springframework.boot.web.client.RestTemplateBuilder
import java.io.Serializable

/**
 * PayPal VO(s)
 *
 * @author Serhii Shymkiv
 */

interface FundingInstrument

data class Paypal(
    val client: RestTemplateBuilder
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}

data class AccessTokenResponse(
    @field:JsonProperty("access_token")
    val accessToken: String? = null,
    @field:JsonProperty("token_type")
    val tokenType: String? = null,
    @field:JsonProperty("expires_in")
    val expiresIn: String? = null
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}

data class Amount(
    val total: String,
    val currency: String
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}

data class ErrorDetail(
    val field: String,
    val issue: String
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}

data class ErrorResponse(
    val name: String? = null,
    val message: String? = null,
    @field:JsonProperty("information_link")
    val informationLink: String? = null,
    @field:JsonProperty("details")
    val errorDetails: List<ErrorDetail>? = listOf()
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}

data class Link(
    val href: String?,
    val rel: String?,
    val method: String?
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}

data class Payer(
    @field:JsonProperty("payment_method")
    val paymentMethod: String,
    @field:JsonProperty("funding_instruments")
    val fundingInstruments: List<FundingInstrument>
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}

data class Transaction(
    val amount: Amount,
    val description: String
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}

data class PaymentRequest(
    val intent: String,
    val payer: Payer,
    val transactions: List<Transaction>
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}

data class PaymentResponse(
    val id: String? = null,
    val state: String = PaymentResponseState.FAILED.value,
    val links: List<Link>? = listOf(),
    val errorResponse: ErrorResponse? = null
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}

data class CreditCard(
    val number: String,
    val type: String,
    @field:JsonProperty("expire_month")
    val expireMonth: String,
    @field:JsonProperty("expire_year")
    val expireYear: String,
    val cvv2: String,
    @field:JsonProperty("first_name")
    val firstName: String,
    @field:JsonProperty("last_name")
    val lastName: String
) : FundingInstrument, Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}

data class CreditCardWrapper(
    @field:JsonProperty("credit_card")
    val creditCard: CreditCard
) : FundingInstrument, Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
