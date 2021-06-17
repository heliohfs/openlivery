package com.openlivery.service.common.system

import com.openlivery.service.common.domain.Parameters
import com.openlivery.service.common.repository.ParametersRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

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

}