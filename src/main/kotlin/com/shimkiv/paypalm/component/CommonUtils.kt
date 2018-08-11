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

package com.shimkiv.paypalm.component

import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.util.*
import kotlin.experimental.and
import kotlin.experimental.or


/**
 * Common utils component
 *
 * @author Serhii Shymkiv
 */

fun String.mask(
    lastShown: Int,
    mask: String
) =
    if (lastShown >= this.length || lastShown < 0) {
        mask.repeat(this.length)
    } else {
        mask.repeat(
            this.length - lastShown
        ) + this.substring(
            this.length - lastShown
        )
    }

@Component
class CommonUtils(
    private val textEncryptor: TextEncryptor
) {
    private val uniqueRefXor =
        BigInteger(
            "3ffffffffffff",
            16
        )

    fun decryptAndMask(
        value: String
    ) =
        textEncryptor
            .decrypt(value)
            .mask(
                4,
                "*"
            )

    fun generateUniqueRef(pKey: Int): String {
        val uniqueRefRandom =
            Random(pKey.toLong())
        val rndBuf =
            ByteArray(2)

        uniqueRefRandom
            .nextBytes(
                rndBuf
            )

        rndBuf[0] = (rndBuf[0] and 0x3)
        rndBuf[0] = (rndBuf[0] or 0x4)

        var bi =
            BigInteger(
                Integer.toString(pKey)
            )

        bi = bi.setBit(37)

        val res = ByteArray(7)
        val tmpArr = bi.toByteArray()

        res[0] = rndBuf[0]
        res[1] = tmpArr[4]
        res[2] = tmpArr[3]
        res[3] = tmpArr[2]
        res[4] = tmpArr[1]
        res[5] = tmpArr[0]
        res[6] = rndBuf[1]

        var bRes = BigInteger(1, res)
        bRes = bRes.xor(uniqueRefXor)

        return bRes
            .toString(36)
            .toUpperCase()
    }

    fun uniqueRefToPkey(
        uniqueRef: String
    ) =
        try {
            var bRes =
                BigInteger(
                    uniqueRef,
                    36
                )

            bRes = bRes.xor(uniqueRefXor)

            val res = ByteArray(5)
            val tmpArr =
                bRes.toByteArray()

            res[0] = tmpArr[5]
            res[1] = tmpArr[4]
            res[2] = tmpArr[3]
            res[3] = tmpArr[2]
            res[4] = tmpArr[1]

            bRes = BigInteger(1, res)
            bRes = bRes.clearBit(37)

            val id = bRes.toInt()

            if (id > 0) {
                id
            } else {
                -1
            }
        } catch (e: Exception) {
            -1
        }
}
