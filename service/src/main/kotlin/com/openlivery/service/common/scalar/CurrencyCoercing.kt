package com.openlivery.service.common.scalar

import graphql.schema.Coercing
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import java.math.BigDecimal
import java.math.RoundingMode

class CurrencyCoercing(private val currencyPrecision: Int) : Coercing<BigDecimal, String> {
    override fun serialize(dataFetcherResult: Any): String {
        return (dataFetcherResult as? BigDecimal)?.setScale(currencyPrecision, RoundingMode.HALF_EVEN)?.toString()
                ?: throw CoercingSerializeException("Expected a BigDecimal object.")
    }

    override fun parseValue(input: Any): BigDecimal {
        return try {
            if (input is String) {
                BigDecimal(input)
            } else {
                throw CoercingParseValueException("Expected a String")
            }
        } catch (e: NumberFormatException) {
            throw CoercingParseValueException("Input is not a valid number", e)
        }
    }

    override fun parseLiteral(input: Any): BigDecimal {
        return try {
            if (input is String) {
                BigDecimal(input)
            } else {
                throw CoercingParseValueException("Expected a String")
            }
        } catch (e: NumberFormatException) {
            throw CoercingParseValueException("Input is not a valid number", e)
        }
    }

}