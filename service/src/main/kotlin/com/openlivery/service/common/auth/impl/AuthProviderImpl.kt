package com.openlivery.service.common.auth.impl

import com.openlivery.service.common.auth.AuthProvider
import com.openlivery.service.common.domain.entity.User
import com.openlivery.service.common.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class AuthProviderImpl(
        private var userRepository: UserRepository,
        private var request: HttpServletRequest,
) : AuthProvider {

    override val id: String
        get() = if (isAnonymous) sessionId
        else user?.id?.toString() ?: throw error("User is not registered")

    override val authentication: Authentication
        get() {
            return SecurityContextHolder.getContext().authentication
        }

    override val isAnonymous: Boolean
        get() {
            val principal = this.authentication.principal
            return principal is String && principal.equals("anonymousUser", true)
        }

    override val user: User?
        get() = if (isAnonymous) null
        else this.authentication.name?.run(userRepository::findOneByOauthId)
                ?.orElseThrow { error("User not registered") }

    override val sessionId: String
        get() = Optional.ofNullable(request.getSession(false))
                .orElse(request.getSession(true))
                .apply { maxInactiveInterval = 60 * 60 * 5 }
                .id
}