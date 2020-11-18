package com.akeijser.igtrader.api.epics

import com.akeijser.igtrader.api.testhelpers.EpicDetailsFixture
import com.akeijser.igtrader.api.testhelpers.EpicFixture
import com.akeijser.igtrader.api.testhelpers.EpicsFixture
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class EpicServiceTest {

    private val epicService: EpicService = mockk()

    @Nested
    inner class `Test Epic service class` {

        @Test
        fun `Test get epics for markets`(){

            //Prepare
            val marketId = 123456
            val epicList = listOf(EpicFixture.create())
            every { epicService.getEpicsForMarketId(marketId) } answers {epicList}

            //Execute
            val result = epicService.getEpicsForMarketId(marketId)

            //Assert
            assertThat(result).isEqualTo(epicList)
        }


    }

    @Test
    fun `Test getEpicsForMarket`() {

        //Prepare
        val marketId = 1
        val epics = EpicsFixture.create().nodes!!
        every {epicService.getEpicsForMarketId(marketId)} answers {epics}

        //Execute
        val result = epicService.getEpicsForMarketId(marketId)

        //Assert
        assertThat(result).isEqualTo(epics)
    }

}