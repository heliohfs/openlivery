package com.openlivery.service.common.auth.impl

import com.openlivery.service.common.auth.IAuthenticationFacade
import com.openlivery.service.common.domain.User
import com.openlivery.service.common.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationFacade(
        var userRepository: UserRepository
) : IAuthenticationFacade {
    override val authentication: Authentication
        get() {
            val auth = SecurityContextHolder.getContext().authentication
            return auth
        }

    override val user: User
        get() {
            val oauthId = SecurityContextHolder.getContext().authentication.name
            return userRepository.findOneByOauthId(oauthId)
                    .orElseThrow { error("User not registered") }
        }


}