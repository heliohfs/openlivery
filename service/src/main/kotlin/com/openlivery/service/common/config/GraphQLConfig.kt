package com.openlivery.service.common.config

import com.openlivery.service.common.scalar.CurrencyCoercing
import com.openlivery.service.common.scalar.DateTimeCoercing
import graphql.Scalars
import graphql.schema.GraphQLScalarType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GraphQLConfig {

    @Bean
    fun createDateTimeScalar(): GraphQLScalarType {
        return GraphQLScalarType.newScalar()
                .name("DateTime")
                .coercing(DateTimeCoercing())
                .build()
    }

    @Bean
    fun createCurrencyScalar(): GraphQLScalarType {
        return GraphQLScalarType.newScalar()
                .name("Currency")
                .coercing(CurrencyCoercing(2))
                .build()
    }

    @Bean
    fun createBigDecimalScalar(): GraphQLScalarType {
        return Scalars.GraphQLBigDecimal
    }

    @Bean
    fun createBigIntegerScalar(): GraphQLScalarType {
        return Scalars.GraphQLBigInteger
    }

}