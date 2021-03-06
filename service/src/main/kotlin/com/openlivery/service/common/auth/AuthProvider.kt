package com.openlivery.service.common.auth

import com.openlivery.service.common.domain.entity.User
import org.springframework.security.core.Authentication

interface AuthProvider {
    val id: String
    val user: User?
    val sessionId: String
    val authentication: Authentication
    val isAnonymous: Boolean
}