package com.akeijser.igtrader.api.epics

import com.akeijser.igtrader.api.markets.MarketsService
import com.akeijser.igtrader.api.testhelpers.AbstractFeatureTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class EpicServiceIntegrationTest(@Autowired private val epicService: EpicService, @Autowired private val marketsService: MarketsService): AbstractFeatureTest() {

    @Nested
    inner class `Test epic service` {

        @Test
        fun `Test getting a list of epic details for single epics`(){

            val market =  marketsService.getMarkets().first()

            val epicDetails = epicService.getEpicsForMarketId(market.id)

            assertThat(epicDetails).isNotNull
        }
    }
}