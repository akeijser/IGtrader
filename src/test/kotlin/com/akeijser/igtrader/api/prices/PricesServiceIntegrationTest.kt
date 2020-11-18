package com.akeijser.igtrader.api.prices

import com.akeijser.igtrader.api.testhelpers.AbstractFeatureTest
import com.akeijser.igtrader.domain.PriceRequest
import com.akeijser.igtrader.dto.ResolutionDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

internal class PricesServiceIntegrationTest(@Autowired private val pricesService: PricesService) : AbstractFeatureTest() {

    @Nested
    inner class `Use IG Trader API to find prices`{
        @Test
        fun pricesTestLocalDateTime(){
            val priceRequest = PriceRequest("CS.D.BITCOIN.CFD.IP", ResolutionDTO.HOUR, LocalDateTime.now().minusDays(2), LocalDateTime.now())

            assertThat(priceRequest).isNotNull
        }

        //todo: does not work when both test cases are run. Ze werken wel afzonderlijk
        @Test
        fun pricesTest(){
            val priceRequest = PriceRequest("CS.D.BITCOIN.CFD.IP", ResolutionDTO.HOUR, null, null, 2)
            assertThat(priceRequest).isNotNull
        }
    }
}