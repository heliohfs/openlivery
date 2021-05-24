package com.openlivery.service.common.auth

import com.openlivery.service.common.domain.User
import org.springframework.security.core.Authentication

interface IAuthenticationFacade {
    val user: User
    val authentication: Authentication
}