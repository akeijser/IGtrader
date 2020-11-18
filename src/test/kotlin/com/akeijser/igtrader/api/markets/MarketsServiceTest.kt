package com.akeijser.igtrader.api.markets

import com.akeijser.igtrader.api.testhelpers.MarketFixture
import com.akeijser.igtrader.api.testhelpers.MarketsFixture
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class MarketsServiceTest {

    private val marketsService : MarketsService = mockk()

    @Nested
    inner class `Test MarketService interface`(){

        @Test
        fun `Test getMarkets`() {

            //Prepare
            val markets = listOf(MarketFixture.create())
            every { marketsService.getMarkets() } answers { markets }

            //Execute
            val result = marketsService.getMarkets()

            //Assert
            assertThat(result).isEqualTo(markets)
        }
    }
}