package com.akeijser.igtrader.api.prices

import com.akeijser.igtrader.api.testhelpers.PriceDetailsFixture
import com.akeijser.igtrader.api.testhelpers.PriceRequestFixture
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class PricesServiceTest {

    private val pricesService : PricesService = mockk()

    @Nested
    inner class `Test getPrices()`{

        @Test
        fun `Test getting prices with no start-end date`(){

            //Prepare
            val priceRequest = PriceRequestFixture.create()
            val priceDetails = listOf(PriceDetailsFixture.create())
            every { pricesService.getPrices(priceRequest) } answers {priceDetails}

            //Execute
            val result = pricesService.getPrices(priceRequest)

            //Assert
            assertThat(result).isEqualTo(priceDetails)
        }
    }
}