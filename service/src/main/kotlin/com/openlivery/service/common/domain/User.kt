package com.openlivery.service.common.domain

import javax.persistence.*

@Entity(name = "User")
@Table(name = "\"user\"")
@Inheritance(strategy = InheritanceType.JOINED)
open class User(
        @Column(name = "oauth_id")
        open val oauthId: String,

        @Column(name = "complete_name")
        open var completeName: String,

        @Column(name = "phone_number")
        open var phoneNumber: String
) : BaseEntity() {

    @Column(name = "email")
    open var email: String? = null

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = [JoinColumn(name = "user_id")],
            inverseJoinColumns = [JoinColumn(name = "authority_id")]
    )
    open var authorities: MutableSet<Authority> = mutableSetOf()

}