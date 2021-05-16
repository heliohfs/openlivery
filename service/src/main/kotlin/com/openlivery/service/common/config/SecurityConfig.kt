package com.openlivery.service.common.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Value("\${auth0.audience}")
    private lateinit var audience: String

    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private lateinit var issuer: String

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/graphql").permitAll() // GET requests don't need auth
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .decoder(jwtDecoder())
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
    }


    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val converter = JwtGrantedAuthoritiesConverter()
        converter.setAuthoritiesClaimName("permissions")
        converter.setAuthorityPrefix("")

        val jwtConverter = JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(converter)
        return jwtConverter;
    }

    fun jwtDecoder(): JwtDecoder {
        val withAudience: OAuth2TokenValidator<Jwt> = AudienceValidator(audience)
        val withIssuer: OAuth2TokenValidator<Jwt> = JwtValidators.createDefaultWithIssuer(issuer)
        val validator: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(withAudience, withIssuer)
        val jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer) as NimbusJwtDecoder
        jwtDecoder.setJwtValidator(validator)
        return jwtDecoder
    }

}