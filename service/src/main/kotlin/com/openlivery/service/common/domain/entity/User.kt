package com.openlivery.service.common.domain.entity

import javax.persistence.*

@Entity
@Table(name = "\"user\"")
@Inheritance(strategy = InheritanceType.JOINED)
open class User(
        @Column(name = "oauth_id")
        open val oauthId: String,
) : BaseEntity() {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = [JoinColumn(name = "user_id")],
            inverseJoinColumns = [JoinColumn(name = "authority_id")]
    )
    open var authorities: MutableSet<Authority> = mutableSetOf()

}