package com.openlivery.service.common.auth.impl

import com.openlivery.service.common.auth.IAuthenticationFacade
import com.openlivery.service.common.domain.entity.User
import com.openlivery.service.common.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class AuthenticationFacade(
        var userRepository: UserRepository,
        var request: HttpServletRequest,
) : IAuthenticationFacade {

    override val id: String
        get() = if (isAnonymous) this.sessionId
        else Optional.ofNullable(this.user.id)
                .orElseThrow { error("User is not registered") }
                .toString()

    override val authentication: Authentication
        get() {
            return SecurityContextHolder.getContext().authentication
        }

    override val isAnonymous: Boolean
        get() {
            val principal = this.authentication.principal
            return principal is String && principal.equals("anonymousUser", true)
        }

    override val user: User
        get() = Optional.ofNullable(this.authentication.name)
                .orElseThrow { error("User is not authenticated") }
                .let { userRepository.findOneByOauthId(it) }
                .orElseThrow { error("User not registered") }

    override val sessionId: String
        get() = Optional.ofNullable(request.getSession(false))
                .orElse(request.getSession(true))
                .apply { maxInactiveInterval = 60 * 60 }
                .id
}