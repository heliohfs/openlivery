package com.openlivery.service.common.repository

import com.openlivery.service.common.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, Long> {

    fun findOneByOauthId(oauthId: String): Optional<User>

    @Query("""
        select id
        from "user"
        where oauth_id = :oauthId
    """, nativeQuery = true)
    fun findIdByOauthId(oauthId: String): Optional<Long>

    @Query("""
        select auth.authority_name
        from "user", user_authority user_auth, authority auth
        where "user".oauth_id = :oauthId and 
            "user".id = user_auth.user_id and 
            user_auth.authority_id = auth.id
    """, nativeQuery = true)
    fun findAuthoritiesByOauthId(oauthId: String): List<String>
}