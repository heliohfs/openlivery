package com.openlivery.service.common.config

import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2ErrorCodes
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.util.Assert

class AudienceValidator(
        private val audience: String
): OAuth2TokenValidator<Jwt> {

    init {
        Assert.hasText(this.audience, "Audience is null or empty")
    }

    override fun validate(token: Jwt): OAuth2TokenValidatorResult {
        val audiences: List<String> = token.audience
        if(audiences.contains(audience)) {
            return OAuth2TokenValidatorResult.success()
        }
        val error = OAuth2Error(OAuth2ErrorCodes.INVALID_TOKEN)
        return OAuth2TokenValidatorResult.failure(error)
    }

}