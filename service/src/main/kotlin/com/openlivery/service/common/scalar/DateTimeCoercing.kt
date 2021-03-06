package com.openlivery.service.common.scalar

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeParseException

class DateTimeCoercing: Coercing<LocalDateTime, String> {
    override fun serialize(dataFetcherResult: Any): String {
        return (dataFetcherResult as? LocalDateTime)?.toString()
                ?: throw CoercingSerializeException("Expected a LocalDateTime object.")
    }

    override fun parseValue(input: Any): LocalDateTime {
        return try {
            if (input is String) {
                OffsetDateTime.parse(input)
                        .withOffsetSameInstant(ZoneOffset.UTC)
                        .toLocalDateTime()
            } else {
                throw CoercingParseValueException("Expected a String")
            }
        } catch (e: DateTimeParseException) {
            throw CoercingParseValueException(String.format("Not a valid date: '%s'.", input), e)
        }
    }

    override fun parseLiteral(input: Any): LocalDateTime {
        return if (input is StringValue) {
            try {
                OffsetDateTime.parse(input.value)
                        .withOffsetSameInstant(ZoneOffset.UTC)
                        .toLocalDateTime()
            } catch (e: DateTimeParseException) {
                throw CoercingParseLiteralException(e)
            }
        } else {
            throw CoercingParseLiteralException("Expected a StringValue.")
        }
    }

}