package com.openlivery.service.common.system

import com.openlivery.service.common.domain.entity.Parameters
import com.openlivery.service.common.repository.ParametersRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalTime
import java.time.ZoneOffset

@Service
class SystemParameters(
        private val repository: ParametersRepository
) {

    fun getParameters(): Parameters = repository.findById(1)
            .orElse(Parameters())

    fun setMinDeliveryFeeValue(value: BigDecimal): Parameters {
        val parameters = getParameters()
        parameters.minDeliveryFeeValue = value
        return repository.save(parameters)
    }

    fun setDeliveryFeeValuePerUnit(value: BigDecimal): Parameters {
        val parameters = getParameters()
        parameters.deliveryFeeValuePerUnit = value
        return repository.save(parameters)
    }

    fun setMinDeliveryFeeDistanceThreshold(value: Int): Parameters {
        val parameters = getParameters()
        parameters.minDeliveryFeeDistanceThreshold = value
        return repository.save(parameters)
    }

    fun setMaximumDeliveryRadius(value: Int): Parameters {
        val parameters = getParameters()
        parameters.maximumDeliveryRadius = value
        return repository.save(parameters)
    }

    fun setShutdownOrderCreationAt(value: LocalTime?): Parameters {
        val parameters = getParameters()
        parameters.shutdownOrderCreationAt = value
        return repository.save(parameters)
    }

    fun setStartupOrderCreationAt(value: LocalTime?): Parameters {
        val parameters = getParameters()
        parameters.startupOrderCreationAt = value
        return repository.save(parameters)
    }

    //TODO: fix
    fun isOpenedToOrders(): Boolean {
        val parameters = getParameters()
        val start = parameters.startupOrderCreationAt
        val stop = parameters.shutdownOrderCreationAt
        if (start != null && stop != null) {
            val now = LocalTime.now(ZoneOffset.UTC)
            val isBetween = !now.isBefore(start) && now.isBefore(stop)
            return if (start.isAfter(stop)) !isBetween else isBetween
        }
        return true
    }

}