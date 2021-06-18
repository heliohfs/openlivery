package com.openlivery.service.common.config

import com.openlivery.service.common.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
        var userRepository: UserRepository
) : WebSecurityConfigurerAdapter() {

    @Value("\${auth0.audience}")
    private lateinit var audience: String

    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private lateinit var issuer: String

    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/graphql").permitAll()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .decoder(jwtDecoder())
                .jwtAuthenticationConverter(jwtAuthenticationConverter())
    }


    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val converter = JwtAuthenticationConverter()
        converter.setJwtGrantedAuthoritiesConverter { jwt: Jwt ->
            userRepository.findAuthoritiesByOauthId(jwt.subject)
                    .map { name -> SimpleGrantedAuthority(name) }
        }
        return converter
    }

    fun jwtDecoder(): JwtDecoder {
        val withAudience = AudienceValidator(audience)
        val withIssuer = JwtValidators.createDefaultWithIssuer(issuer)
        val validator = DelegatingOAuth2TokenValidator(withAudience, withIssuer)
        val jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer) as NimbusJwtDecoder
        jwtDecoder.setJwtValidator(validator)
        return jwtDecoder
    }

}