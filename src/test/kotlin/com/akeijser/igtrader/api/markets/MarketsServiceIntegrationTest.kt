package com.akeijser.igtrader.api.markets

import com.akeijser.igtrader.api.testhelpers.AbstractFeatureTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired

internal class MarketsServiceIntegrationTest(@Autowired private val marketsService: MarketsService) : AbstractFeatureTest() {

    @Nested
    inner class `Use IG API to find market information`{

        @Test
        fun `Test get markets`(){

            try {
                marketsService.getMarkets()
            } catch (e : Exception) {
                assert(false)
            }

            assert(true)
        }
    }
}