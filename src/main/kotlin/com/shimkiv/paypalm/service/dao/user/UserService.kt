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

package com.shimkiv.paypalm.service.dao.user

import com.shimkiv.paypalm.model.dto.user.User
import com.shimkiv.paypalm.model.dto.user.UserAccessToken
import com.shimkiv.paypalm.model.dto.user.UserSettings
import com.shimkiv.paypalm.model.vo.form.AccountSettingsForm
import com.shimkiv.paypalm.properties.USER_CACHE_NAME
import com.shimkiv.paypalm.repository.user.UserRepository
import mu.KotlinLogging
import org.springframework.beans.factory.getBean
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.BindingResult
import java.util.*

/**
 * User service
 *
 * @author Serhii Shymkiv
 */

private val kLogger = KotlinLogging.logger {}

@Service
@Transactional
@CacheConfig(cacheNames = [USER_CACHE_NAME])
class UserService(
    private val applicationContext: ApplicationContext,
    private val userRepository: UserRepository
) {
    @Cacheable
    fun findByName(name: String?) =
        userRepository
            .findByName(
                name
            )

    @CacheEvict(
        cacheNames = [USER_CACHE_NAME],
        allEntries = true,
        beforeInvocation = false
    )
    fun saveAndFlush(user: User) =
        userRepository
            .saveAndFlush(
                user
            )

    fun updateAccountSettings(
        userName: String,
        accountSettingsForm: AccountSettingsForm,
        bindingResult: BindingResult
    ) {
        if (!bindingResult.hasErrors()) {
            findByName(
                userName
            )?.apply {
                val userSettings =
                    this.userSettings

                if (this.userAccessToken == null) {
                    this.userAccessToken =
                            UserAccessToken()
                }

                if (
                    isUpdatedPaypalCredentials(
                        accountSettingsForm,
                        userSettings
                    )
                ) {
                    this.userAccessToken?.apply {
                        creationDate = Calendar.getInstance()
                        expiresIn = 0
                        tokenType = null
                        accessToken = null
                    }
                }

                accountSettingsForm.let {
                    userSettings.apply {
                        firstName =
                                it.firstName
                        lastName =
                                it.lastName
                        email =
                                it.email
                        paypalClientId =
                                it.paypalClientId
                        paypalSecret =
                                it.paypalSecret
                    }
                }

                applicationContext
                    .getBean<UserService>()
                    .saveAndFlush(
                        this
                    )
            }
        } else {
            kLogger.debug(
                "User account settings form validation errors: {}",
                bindingResult
            )
        }
    }

    fun updateAccessToken(
        userName: String,
        userAccessToken: UserAccessToken
    ) {
        findByName(
            userName
        )?.apply {
            if (this.userAccessToken == null) {
                this.userAccessToken =
                        UserAccessToken()
            }

            this.userAccessToken
                ?.also {
                    it.creationDate =
                            userAccessToken
                                .creationDate
                    it.expiresIn =
                            userAccessToken
                                .expiresIn
                    it.tokenType =
                            userAccessToken
                                .tokenType
                    it.accessToken =
                            userAccessToken
                                .accessToken
                }

            applicationContext
                .getBean<UserService>()
                .saveAndFlush(
                    this
                )
        }
    }

    private fun isUpdatedPaypalCredentials(
        accountSettingsForm: AccountSettingsForm,
        userSettings: UserSettings
    ) =
        (
                accountSettingsForm
                    .paypalClientId != null &&
                        !accountSettingsForm
                            .paypalClientId
                            .equals(
                                other = userSettings.paypalClientId,
                                ignoreCase = true
                            )
                ) ||
                (
                        accountSettingsForm
                            .paypalSecret != null &&
                                !accountSettingsForm
                                    .paypalSecret
                                    .equals(
                                        other = userSettings.paypalSecret,
                                        ignoreCase = true
                                    )
                        )
}
