package com.akeijser.igtrader.api.epicdetails

import com.akeijser.igtrader.api.epics.EpicService
import com.akeijser.igtrader.api.markets.MarketsService
import com.akeijser.igtrader.api.testhelpers.AbstractFeatureTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class EpicDetailIntegrationTest(
        @Autowired private val epicDetailService: EpicDetailService,
        @Autowired private val epicService: EpicService,
        @Autowired private val marketsService: MarketsService
): AbstractFeatureTest() {


    @Test
    fun `Test getting epic details from IG Trader platform`(){

        //Prepare (kinda bad cause this is using another service to find an epic
        val market = marketsService.getMarkets().first()

        val epic = epicService.getEpicsForMarketId(market.id).first()

        //Execute
        val result = epicDetailService.getEpicDetails(epic)

        assertThat(result).isNotNull
    }


    @Test
    fun `Test getting a list of epic details for multiple epics`(){

        //todo it seems getMarkets does not give me actual markets but something else. Checking with Bart
        val market = marketsService.getMarkets().first()
        val epic = epicService.getEpicsForMarketId(market.id).map { it.name }


        val epicList2 = listOf("CS.D.BITCOIN.CFE.IP","CS.D.BITCOIN.CFD.IP")
        val epicDetails = epicDetailService.getEpicDetails(epicList2, false)

        assertThat(epicDetails).isNotNull

    }

}